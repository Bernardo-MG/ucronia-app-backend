
package com.bernardomg.security.token.schedule;

import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bernardomg.security.token.service.TokenCleanUpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TokenCleanUpScheduleTask {

    private final TokenCleanUpService service;

    public TokenCleanUpScheduleTask(final TokenCleanUpService tokenCleanUpService) {
        super();

        service = Objects.requireNonNull(tokenCleanUpService);
    }

    @Async
    @Scheduled(cron = "0 0 0 1 1/1 *")
    public final void cleanUpTokens() {
        log.info("Starting token cleanup task");
        service.cleanUpTokens();
        log.info("Finished token cleanup task");
    }

}
