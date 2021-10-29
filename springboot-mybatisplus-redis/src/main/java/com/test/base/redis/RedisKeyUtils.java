package com.test.base.redis;


import com.test.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author shuai.zhang
 * @projectName rule-engine
 * @Description
 * @createTime 2020-06-11 13:53
 */
@Slf4j
public class RedisKeyUtils {

    public static String build(String... keys){
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String key : keys){
            
            if(StringUtils.isEmpty(key)){
                log.error("缓存拼接的key...=[]为空", key);
                throw new BaseException("缓存拼接的key为空");
            }

            boolean isLastSeparator =  index >= keys.length && key.endsWith(":");
            if(isLastSeparator){
                key = key.substring(0, key.length() - 1);
            }

            sb.append(key);

            boolean isSeparator =  index < keys.length && !key.endsWith(":");
            if(isSeparator){
                sb.append(":");
            }

            index++;
        }


        return sb.toString();
    }
}