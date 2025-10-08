
package com.bernardomg.association.configuration;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
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
public class AsyncConfiguration implements AsyncConfigurer {

    private final AsyncProperties asyncProperties;

    public AsyncConfiguration(final AsyncProperties asyncProps) {
        super();

        asyncProperties = asyncProps;
    }

    @Override
    @Bean("SchedulingTaskExecutor")
    public SchedulingTaskExecutor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor;

        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperties.executor()
            .corePoolSize());
        executor.setMaxPoolSize(asyncProperties.executor()
            .maxPoolSize());
        executor.setQueueCapacity(asyncProperties.executor()
            .queueCapacity());
        executor.setThreadNamePrefix(asyncProperties.executor()
            .threadNamePrefix());
        executor.setThreadGroupName(asyncProperties.executor()
            .groupName());
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
        scheduler.setThreadNamePrefix(asyncProperties.scheduler()
            .threadNamePrefix());
        scheduler.setThreadGroupName(asyncProperties.scheduler()
            .groupName());
        scheduler.setPoolSize(asyncProperties.scheduler()
            .poolSize());
        scheduler.setErrorHandler(TaskUtils.getDefaultErrorHandler(false));

        return scheduler;
    }

}
