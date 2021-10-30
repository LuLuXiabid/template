package com.xiaoli.web.cn.controller;

import com.xiaoli.web.cn.Bean.Teacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TeacherController {

    @Resource
    private Teacher teacher;

    @GetMapping("/222")
    public String getStudent(){
        return teacher.toString();
    }
}
