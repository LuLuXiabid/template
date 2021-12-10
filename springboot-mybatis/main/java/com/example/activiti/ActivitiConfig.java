package com.example.activiti;

import javax.sql.DataSource;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration() {
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        //避免发布的图片和xml中文出现乱码
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setAnnotationFontName("宋体");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        return configuration;
    }

    @Bean
    public ProcessEngine processEngine() {
        return processEngineConfiguration().buildProcessEngine();
    }

    /**
     * 仓库服务类，用于管理bpmn文件与流程图片
     * @return
     */
    @Bean
    public RepositoryService repositoryService(){
        return processEngine().getRepositoryService();
    }

    /**
     * 流程运行服务类，用于获取流程执行相关信息
     * @return
     */
    @Bean
    public RuntimeService runtimeService(){
        return processEngine().getRuntimeService();
    }

    /**
     * 任务服务类，用户获取任务信息
     * @return
     */
    @Bean
    public TaskService taskService(){
        return processEngine().getTaskService();
    }


    /**
     * 获取正在运行或已经完成的流程实例历史信息
     * @return
     */
    @Bean
    public HistoryService historyService(){
        return processEngine().getHistoryService();
    }

    /**
     * 流程引擎的管理与维护
     * @return
     */
    @Bean
    public ManagementService managementService(){
        return processEngine().getManagementService();
    }

    /**
     * 创建、更新、删除，查询群组和用户
     * @return
     */
    @Bean
    public IdentityService identityService(){
        return processEngine().getIdentityService();
    }

}