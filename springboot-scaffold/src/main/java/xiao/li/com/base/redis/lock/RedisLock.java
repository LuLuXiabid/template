package xiao.li.com.base.redis.lock;

/**
 * RedisLock
 * Redis的分布式锁对象
 *
 * @author hao.wang
 * @version 1.0
 * @createDate 2020/9/1 16:50
 */
public interface RedisLock extends AutoCloseable {
    /**
     * 释放分布式锁
     */
    void unlock();
}
