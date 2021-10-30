package com.xiaoli.web.cn.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;



/**
 * 通过统一前缀取其他.properties文件的的属性
 * 经测试，spring会自动做大小写映射
 */

@PropertySource(value = "classpath:redis/redis-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "redis")
@Component
@Data
public class RedisConfig {
    private String userName;
    private String passWord;
}
