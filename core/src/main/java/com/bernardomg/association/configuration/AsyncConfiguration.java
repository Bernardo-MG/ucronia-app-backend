/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
