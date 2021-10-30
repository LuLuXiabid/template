package com.xiaoli.web.cn.Bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * 直接取application.properties的属性
 *
 * 根据测试，随着多环境的加入，会根据不同的环境拿到不同的配置
 */
@Data
@Configuration
public class Student {
    @Value("${xiao.li.name}")
    private String name;
    @Value("${xiao.li.age}")
    private int age;
}
