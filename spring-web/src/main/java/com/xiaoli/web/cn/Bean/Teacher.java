package com.xiaoli.web.cn.Bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 通过统一前缀取application.properties的属性
 */
@ConfigurationProperties(prefix = "teacher")
@Component
@Data
public class Teacher {
    private String name;
    private int age;
}
