
package com.bernardomg.security.token.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.token.service.PersistentUserTokenCleanUpService;
import com.bernardomg.security.token.test.config.ConsumedToken;
import com.bernardomg.security.token.test.config.ExpiredToken;
import com.bernardomg.security.token.test.config.RevokedToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.user.test.config.ValidUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenCleanUpService")
public class ITPersistentUserTokenCleanUpService {

    @Autowired
    private PersistentUserTokenCleanUpService service;

    @Autowired
    private UserTokenRepository               userTokenRepository;

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
