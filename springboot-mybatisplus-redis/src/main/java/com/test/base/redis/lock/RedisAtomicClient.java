package com.test.base.redis.lock;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;

/**
 * RedisAtomicClient
 * 提供Redis一些不直接支持的原子性的操作,很多实现采用了lua脚本
 *
 * @author hao.wang
 * @version 1.0
 * @createDate 2020/9/1 16:49
 */
@Component
public class RedisAtomicClient {

    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String COMPARE_AND_DELETE =
            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call('del',KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    public RedisAtomicClient(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = new StringRedisTemplate();
        this.stringRedisTemplate.setConnectionFactory(redisTemplate.getConnectionFactory());
        this.stringRedisTemplate.afterPropertiesSet();
    }

    /**
     * 获取redis的分布式锁，内部实现使用了redis的setnx。只会尝试一次，如果锁定失败返回null，如果锁定成功则返回RedisLock对象，调用方需要调用RedisLock.unlock()方法来释放锁.
     * <br/>使用方法：
     * <pre>
     * RedisLock lock = redisAtomicClient.getLock(key, 2);
     * if(lock != null){
     *      try {
     *          //lock succeed, do something
     *      }finally {
     *          lock.unlock();
     *      }
     * }
     * </pre>
     * 由于RedisLock实现了AutoCloseable,所以可以使用更简介的使用方法:
     * <pre>
     *  try(RedisLock lock = redisAtomicClient.getLock(key, 2)) {
     *      if (lock != null) {
     *          //lock succeed, do something
     *      }
     *  }
     * </pre>
     *
     * @param key           要锁定的key
     * @param expireSeconds key的失效时间
     * @return 获得的锁对象（如果为null表示获取锁失败），后续可以调用该对象的unlock方法来释放锁.
     */
    public RedisLock getLock(final String key, long expireSeconds) {
        return getLock(key, expireSeconds, 0, 0);
    }

    /**
     * 获取redis的分布式锁，内部实现使用了redis的setnx。如果锁定失败返回null，如果锁定成功则返回RedisLock对象，调用方需要调用RedisLock.unlock()方法来释放锁
     * 此方法在获取失败时会自动重试指定的次数,由于多次等待会阻塞当前线程，请尽量避免使用此方法
     *
     * @param key                     要锁定的key
     * @param expireSeconds           key的失效时间
     * @param maxRetryTimes           最大重试次数,如果获取锁失败，会自动尝试重新获取锁；
     * @param retryIntervalTimeMillis 每次重试之前sleep等待的毫秒数
     * @return 获得的锁对象（如果为null表示获取锁失败），后续可以调用该对象的unlock方法来释放锁.
     */
    public RedisLock getLock(final String key, final long expireSeconds, int maxRetryTimes, long retryIntervalTimeMillis) {
        final String value = key.hashCode() + "";

        int maxTimes = maxRetryTimes + 1;
        for (int i = 0; i < maxTimes; i++) {
            String status = stringRedisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    Jedis jedis = (Jedis) connection.getNativeConnection();
                    return jedis.set(key, value, "nx", "ex", expireSeconds);
                }
            });
            //抢到锁
            if ("OK".equals(status)) {
                return new RedisLockInner(stringRedisTemplate, key, value);
            }
            if (retryIntervalTimeMillis > 0) {
                try {
                    Thread.sleep(retryIntervalTimeMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }

        return null;
    }

    /**
     * RedisLockInner
     *
     * @author hao.wang
     * @createDate 2020/9/1 16:51
     * @version 1.0
     */
    private class RedisLockInner implements RedisLock {
        private StringRedisTemplate stringRedisTemplate;
        private String key;
        private String expectedValue;

        protected RedisLockInner(StringRedisTemplate stringRedisTemplate, String key, String expectedValue) {
            this.stringRedisTemplate = stringRedisTemplate;
            this.key = key;
            this.expectedValue = expectedValue;
        }

        /**
         * 释放redis分布式锁
         */
        @Override
        public void unlock() {
            List<String> keys = Collections.singletonList(key);
            stringRedisTemplate.execute(new DefaultRedisScript<>(COMPARE_AND_DELETE, String.class), keys, expectedValue);
        }

        @Override
        public void close() {
            this.unlock();
        }
    }

}
