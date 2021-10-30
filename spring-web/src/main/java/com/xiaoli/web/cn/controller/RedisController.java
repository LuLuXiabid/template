package com.xiaoli.web.cn.controller;

import com.xiaoli.web.cn.Bean.Student;
import com.xiaoli.web.cn.config.RedisConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {
    @Resource
    private RedisConfig redisConfig;

    @GetMapping("/333")
    public String getStudent(){
        return redisConfig.toString();
    }
}
