
package com.bernardomg.security.user.token.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.user.test.config.ValidUser;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.user.token.service.SpringUserTokenService;
import com.bernardomg.security.user.token.test.config.annotation.ConsumedToken;
import com.bernardomg.security.user.token.test.config.annotation.ExpiredToken;
import com.bernardomg.security.user.token.test.config.annotation.RevokedToken;
import com.bernardomg.security.user.token.test.config.annotation.ValidToken;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SpringUserTokenService - clean up")
public class ITSpringUserTokenServiceCleanUp {

    @Autowired
    private SpringUserTokenService service;

    @Autowired
    private UserTokenRepository    userTokenRepository;

    @Test
    @DisplayName("Removes consumed tokens")
    @ValidUser
    @ConsumedToken
    void testCleanUpTokens_Consumed() {
        final long count;

        service.cleanUpTokens();

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Does nothing when there are no tokens")
    void testCleanUpTokens_Empty() {
        final long count;

        service.cleanUpTokens();

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Removes expired tokens")
    @ValidUser
    @ExpiredToken
    void testCleanUpTokens_Expired() {
        final long count;

        service.cleanUpTokens();

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Removes revoked tokens")
    @ValidUser
    @RevokedToken
    void testCleanUpTokens_Revoked() {
        final long count;

        service.cleanUpTokens();

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("Does not remove valid tokens")
    @ValidUser
    @ValidToken
    void testCleanUpTokens_Valid() {
        final long count;

        service.cleanUpTokens();

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

}
