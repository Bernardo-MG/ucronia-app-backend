
package com.bernardomg.security.user.token.test.service.integration;

import java.time.LocalDateTime;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.security.user.token.model.DefaultUserTokenPartial;
import com.bernardomg.security.user.token.model.UserTokenPartial;
import com.bernardomg.security.user.token.service.SpringUserTokenService;
import com.bernardomg.security.user.token.test.config.annotation.RevokedToken;
import com.bernardomg.security.user.token.test.config.annotation.ValidToken;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@DisplayName("SpringUserTokenService - patch validation")
class ITSpringUserTokenServicePatchValidation {

    @Autowired
    private SpringUserTokenService service;

    @Test
    @DisplayName("When trying to set the expiration date before the current date an exception is thrown")
    @OnlyUser
    @ValidToken
    void testUpdate_ExpireBeforeNow() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final UserTokenPartial request;
        final LocalDateTime    date;

        date = LocalDateTime.now()
            .minusDays(1);
        request = DefaultUserTokenPartial.builder()
            .expirationDate(date)
            .build();

        execution = () -> service.patch(1L, request);

        failure = FieldFailure.of("expirationDate.beforeToday", "expirationDate", "beforeToday", date);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("When trying to enable a revoked token an exception is thrown")
    @OnlyUser
    @RevokedToken
    void testUpdate_RemoveRevoked() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final UserTokenPartial request;

        request = DefaultUserTokenPartial.builder()
            .revoked(false)
            .build();

        execution = () -> service.patch(1L, request);

        failure = FieldFailure.of("revoked.invalidValue", "revoked", "invalidValue", false);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

}
