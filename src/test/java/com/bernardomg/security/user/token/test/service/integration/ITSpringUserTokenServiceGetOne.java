
package com.bernardomg.security.user.token.test.service.integration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.security.user.token.model.UserToken;
import com.bernardomg.security.user.token.service.SpringUserTokenService;
import com.bernardomg.security.user.token.test.config.annotation.ConsumedToken;
import com.bernardomg.security.user.token.test.config.annotation.ExpiredToken;
import com.bernardomg.security.user.token.test.config.annotation.RevokedToken;
import com.bernardomg.security.user.token.test.config.annotation.ValidToken;
import com.bernardomg.security.user.token.test.config.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SpringUserTokenService - get one")
class ITSpringUserTokenServiceGetOne {

    @Autowired
    private SpringUserTokenService service;

    @Test
    @DisplayName("Returns a token when the token is consumed")
    @OnlyUser
    @ConsumedToken
    void testGetOne_Consumed() {
        final Optional<UserToken> token;

        token = service.getOne(1L);

        Assertions.assertThat(token)
            .isNotEmpty();
    }

    @Test
    @DisplayName("Returns a token when the token is expired")
    @OnlyUser
    @ExpiredToken
    void testGetOne_Expired() {
        final Optional<UserToken> token;

        token = service.getOne(1L);

        Assertions.assertThat(token)
            .isNotEmpty();
    }

    @Test
    @DisplayName("With a not existing token, an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        execution = () -> service.getOne(1L);

        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(InvalidIdException.class);
    }

    @Test
    @DisplayName("Returns a token when the token is revoked")
    @OnlyUser
    @RevokedToken
    void testGetOne_Revoked() {
        final Optional<UserToken> token;

        token = service.getOne(1L);

        Assertions.assertThat(token)
            .isNotEmpty();
    }

    @Test
    @DisplayName("Returns a token when the token is valid")
    @OnlyUser
    @ValidToken
    void testGetOne_Valid() {
        final Optional<UserToken> token;

        token = service.getOne(1L);

        Assertions.assertThat(token)
            .isNotEmpty();
    }

    @Test
    @DisplayName("Returns all the token data when the token is valid")
    @OnlyUser
    @ValidToken
    void testGetOne_Valid_data() {
        final Optional<UserToken> read;
        final UserToken           token;

        read = service.getOne(1L);

        token = read.get();

        Assertions.assertThat(token.getId())
            .isEqualTo(1);
        Assertions.assertThat(token.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(token.getName())
            .isEqualTo("Admin");
        Assertions.assertThat(token.getScope())
            .isEqualTo(TokenConstants.SCOPE);
        Assertions.assertThat(token.getToken())
            .isEqualTo(TokenConstants.TOKEN);
        Assertions.assertThat(token.isConsumed())
            .isFalse();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
        Assertions.assertThat(token.getCreationDate())
            .isEqualTo(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0));
        Assertions.assertThat(token.getExpirationDate())
            .isEqualTo(LocalDateTime.of(2030, Month.FEBRUARY, 1, 0, 0));
    }

}
