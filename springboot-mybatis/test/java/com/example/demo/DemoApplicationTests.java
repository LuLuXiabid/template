package com.example.demo;

import com.example.entity.User;
import com.example.service.UserService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


	@Autowired
	private UserService userService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Test
	public void contextLoads() {
		User sel = userService.Sel(1);
		System.out.println(sel.toString());
	}


	@Test
	public void demoTest() {
		// 1、发布流程
		Deployment deployment = repositoryService.createDeployment().name("请假小流程").addClasspathResource("act/testDemo.bpmn20.xml").deploy();

//		// 2、启动一个流程实例，由于两个环节审批的人都是写死的test，所以这边在启动流程的时候未透传下一环节处理人
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vocation");
//
//		// 3、查询所有任务
//		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
//
//		// 4、提交到总经理审批即完成任务，同样不需要传递变量
//		Task task = tasks.get(0);
//		taskService.complete(task.getId());
	}


}
