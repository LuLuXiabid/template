package xiao.li.com.base.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xiao.li.com.base.redis.RedisDao;
import xiao.li.com.base.redis.RedisKeyUtils;

import java.util.concurrent.TimeUnit;

/***
* 多系统 <br>
*
* @param
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2021/10/29 10:56 <br>
**/
@Component
@Slf4j
public class JwtLocalManager {

    private final String JWT_AU_VERSION_PREFIX = "lixiao:jwt:ver";

    @Autowired
    private RedisDao redisDao;


    public void updateLatestJwtAuthVersion(String empNo, long latestAuthVersion, long expireSeconds){
        redisDao.set(RedisKeyUtils.build(JWT_AU_VERSION_PREFIX, empNo), latestAuthVersion, expireSeconds, TimeUnit.SECONDS);
    }

    public boolean isJwtAuthVersionValid(String empNo, long jwtAuthVersion){

        Integer cacheAuthVersion = (Integer) redisDao.get(RedisKeyUtils.build(JWT_AU_VERSION_PREFIX, empNo));
        if(cacheAuthVersion == null){
            return true;
        }

        boolean flag = jwtAuthVersion >= Long.valueOf(cacheAuthVersion);
        if(flag){
            return true;
        }

        log.info("jwt.authVersion失效：cacheAuthVersion=[{}]大于jwtAuthVersion=[{}]", cacheAuthVersion, jwtAuthVersion);
        return false;

    }

}
