package xiao.li.com.base.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author xing1.wang
 * @version 1.0
 * @createDate 2020/11/17 14:12:51
 */
@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {

    /**
     * 核心线程数（默认线程数）线程池创建时候初始化的线程数
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * 最大线程数 线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
     */
    private static final int MAX_POOL_SIZE = 100;

    /**
     * 允许线程空闲时间（单位：默认为秒）当超过了核心线程之外的线程在空闲时间到达之后会被销毁
     */
    private static final int KEEP_ALIVE_TIME = 10;

    /**
     * 缓冲队列数 用来缓冲执行任务的队列
     */
    private static final int QUEUE_CAPACITY = 200;

    /**
     * 线程池名前缀 方便我们定位处理任务所在的线程池
     */
    private static final String THREAD_NAME_PREFIX = "async-service-";


    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}