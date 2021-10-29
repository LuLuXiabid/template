package xiao.li.com.base.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author shuai.zhang
 * @projectName rule-engine
 * @Description
 * @createTime 2020-06-01 14:48
 */
@Component
public class RedisDao {

    @Autowired
    private RedisTemplate redisTemplate;

    public void del(String key){
        redisTemplate.delete(key);
    }

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long time, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Object getLong(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void addSet(String key, String value){
        redisTemplate.opsForSet().add(key, value);
    }

    public void addSet(String key, String[] values){
        redisTemplate.opsForSet().add(key, values);
    }

    public Long removeSet(String key, String value){
        return redisTemplate.opsForSet().remove(key, value);
    }

    public Long removeSet(String key, String[] values){
        return redisTemplate.opsForSet().remove(key, values);
    }

    public Set getSet(String key){
        return redisTemplate.opsForSet().members(key);
    }

    public Boolean isSetMember(String key, Object value){
        return redisTemplate.opsForSet().isMember(key, value);
    }


    public boolean expire(String key, long timeOut, TimeUnit timeUnit){
        return redisTemplate.expire(key, timeOut, timeUnit);
    }
    
    public long incr(String key, long by){
        return redisTemplate.opsForValue().increment(key, by);
    }

}