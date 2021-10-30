package com.xiaoli.web.cn.controller;


import com.xiaoli.web.cn.Bean.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController()
public class StudentController {
    @Resource
    private Student student;

    @GetMapping("/111")
    public String getStudent(){
        return student.toString();
    }
}
