
package com.bernardomg.association.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor;

        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Set the number of initial threads
        executor.setMaxPoolSize(20); // Set the maximum number of threads
        executor.setQueueCapacity(100); // Set the capacity of the queue
        executor.setThreadNamePrefix("Async-"); // Set thread name prefix
        executor.initialize();

        return executor;
    }

}
