
package com.bernardomg.security.user.token.test.api.service.integration;

import java.time.LocalDateTime;
import java.time.Month;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.security.user.token.api.model.UserToken;
import com.bernardomg.security.user.token.api.model.UserTokenPartial;
import com.bernardomg.security.user.token.api.model.UserTokenPatchRequest;
import com.bernardomg.security.user.token.api.service.DefaultUserTokenService;
import com.bernardomg.security.user.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.user.token.test.config.ValidToken;
import com.bernardomg.security.user.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DefaultUserTokenService - get one")
class ITDefaultUserTokenServicePatch {

    @Autowired
    private DefaultUserTokenService service;

    @Autowired
    private UserTokenRepository     userTokenRepository;

    @Test
    @DisplayName("Patching the consumed flag creates no new token")
    @OnlyUser
    @ValidToken
    void testPatch_NotCreated() {
        final UserTokenPartial request;

        request = UserTokenPatchRequest.builder()
            .consumed(true)
            .build();

        service.patch(1L, request);

        userTokenRepository.findById(1l)
            .get();

        Assertions.assertThat(userTokenRepository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Patching a not existing entity throws an exception")
    @OnlyUser
    void testPatch_NotExisting() {
        final UserTokenPartial request;
        final ThrowingCallable execution;

        request = UserTokenPatchRequest.builder()
            .consumed(true)
            .build();

        execution = () -> service.patch(1L, request);

        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(InvalidIdException.class);
    }

    @Test
    @DisplayName("Patching the consumed flag persists an updated token")
    @OnlyUser
    @ValidToken
    void testPatch_Persisted() {
        final PersistentUserToken token;
        final UserTokenPartial    request;

        request = UserTokenPatchRequest.builder()
            .consumed(true)
            .build();

        service.patch(1L, request);

        token = userTokenRepository.findById(1l)
            .get();

        Assertions.assertThat(token.getId())
            .isEqualTo(1);
        Assertions.assertThat(token.getScope())
            .isEqualTo(TokenConstants.SCOPE);
        Assertions.assertThat(token.getToken())
            .isEqualTo(TokenConstants.TOKEN);
        Assertions.assertThat(token.isConsumed())
            .isTrue();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
        Assertions.assertThat(token.getCreationDate())
            .isEqualTo(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0));
        Assertions.assertThat(token.getExpirationDate())
            .isEqualTo(LocalDateTime.of(2030, Month.FEBRUARY, 1, 0, 0));
    }

    @Test
    @DisplayName("Patching the consumed flag returns an updated token")
    @OnlyUser
    @ValidToken
    void testPatch_Returned() {
        final UserToken        token;
        final UserTokenPartial request;

        request = UserTokenPatchRequest.builder()
            .consumed(true)
            .build();

        token = service.patch(1L, request);

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
            .isTrue();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
        Assertions.assertThat(token.getCreationDate())
            .isEqualTo(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0));
        Assertions.assertThat(token.getExpirationDate())
            .isEqualTo(LocalDateTime.of(2030, Month.FEBRUARY, 1, 0, 0));
    }

}
