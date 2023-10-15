
package com.bernardomg.security.user.token.test.api.service.integration;

import java.time.LocalDateTime;
import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.security.user.token.api.model.UserToken;
import com.bernardomg.security.user.token.api.service.DefaultUserTokenService;
import com.bernardomg.security.user.token.test.config.ConsumedToken;
import com.bernardomg.security.user.token.test.config.ExpiredToken;
import com.bernardomg.security.user.token.test.config.RevokedToken;
import com.bernardomg.security.user.token.test.config.ValidToken;
import com.bernardomg.security.user.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DefaultUserTokenService - get all")
class ITDefaultUserTokenServiceGetAll {

    @Autowired
    private DefaultUserTokenService service;

    @Test
    @DisplayName("Returns a token when the token is consumed")
    @OnlyUser
    @ConsumedToken
    void testIsValid_Consumed() {
        final Pageable            pageable;
        final Iterable<UserToken> tokens;

        pageable = Pageable.unpaged();

        tokens = service.getAll(pageable);

        Assertions.assertThat(tokens)
            .hasSize(1);
    }

    @Test
    @DisplayName("Returns a token when the token is expired")
    @OnlyUser
    @ExpiredToken
    void testIsValid_Expired() {
        final Pageable            pageable;
        final Iterable<UserToken> tokens;

        pageable = Pageable.unpaged();

        tokens = service.getAll(pageable);

        Assertions.assertThat(tokens)
            .hasSize(1);
    }

    @Test
    @DisplayName("Doesn't return anything when the token doesn't exist")
    @OnlyUser
    void testIsValid_NotExisting() {
        final Pageable            pageable;
        final Iterable<UserToken> tokens;

        pageable = Pageable.unpaged();

        tokens = service.getAll(pageable);

        Assertions.assertThat(tokens)
            .isEmpty();
    }

    @Test
    @DisplayName("Returns a token when the token is revoked")
    @OnlyUser
    @RevokedToken
    void testIsValid_Revoked() {
        final Pageable            pageable;
        final Iterable<UserToken> tokens;

        pageable = Pageable.unpaged();

        tokens = service.getAll(pageable);

        Assertions.assertThat(tokens)
            .hasSize(1);
    }

    @Test
    @DisplayName("Returns a token when the token is valid")
    @OnlyUser
    @ValidToken
    void testIsValid_Valid() {
        final Pageable            pageable;
        final Iterable<UserToken> tokens;

        pageable = Pageable.unpaged();

        tokens = service.getAll(pageable);

        Assertions.assertThat(tokens)
            .hasSize(1);
    }

    @Test
    @DisplayName("Returns all the token data when the token is valid")
    @OnlyUser
    @ValidToken
    void testIsValid_Valid_data() {
        final Pageable  pageable;
        final UserToken token;

        pageable = Pageable.unpaged();

        token = service.getAll(pageable)
            .iterator()
            .next();

        Assertions.assertThat(token.getId())
            .isEqualTo(1);
        Assertions.assertThat(token.getUsername())
            .isEqualTo("admin");
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
