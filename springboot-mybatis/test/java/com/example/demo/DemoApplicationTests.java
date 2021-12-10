package com.example.demo;

import com.example.entity.User;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		User sel = userService.Sel(1);
		System.out.println(sel.toString());
	}

}
