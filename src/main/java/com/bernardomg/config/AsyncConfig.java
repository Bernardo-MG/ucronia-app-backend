
package com.bernardomg.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.TaskUtils;

import com.bernardomg.async.LoggingAsyncExceptionHandler;

/**
 * Async configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(AsyncProperties.class)
public class AsyncConfig implements AsyncConfigurer {

    @Autowired
    private AsyncProperties asyncProperties;

    @Override
    @Bean("SchedulingTaskExecutor")
    public SchedulingTaskExecutor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor;

        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperties.getExecutor()
            .getCorePoolSize());
        executor.setMaxPoolSize(asyncProperties.getExecutor()
            .getMaxPoolSize());
        executor.setQueueCapacity(asyncProperties.getExecutor()
            .getQueueCapacity());
        executor.setThreadNamePrefix(asyncProperties.getExecutor()
            .getThreadNamePrefix());
        executor.setThreadGroupName(asyncProperties.getExecutor()
            .getGroupName());
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new LoggingAsyncExceptionHandler();
    }

    @Bean("TaskScheduler")
    public TaskScheduler getThreadPoolTaskScheduler() {
        final ThreadPoolTaskScheduler scheduler;

        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix(asyncProperties.getScheduler()
            .getThreadNamePrefix());
        scheduler.setThreadGroupName(asyncProperties.getScheduler()
            .getGroupName());
        scheduler.setPoolSize(asyncProperties.getScheduler()
            .getPoolSize());
        scheduler.setErrorHandler(TaskUtils.getDefaultErrorHandler(false));

        return scheduler;
    }

}
