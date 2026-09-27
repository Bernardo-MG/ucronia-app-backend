/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.outbound.rest.security;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.image.test.configuration.factory.ImageConstants;

@DisplayName("SpringSecurityImageReadAuthorizer")
class TestSpringSecurityImageReadAuthorizer {

    private final ImageReadAuthorizer authorizer = new SpringSecurityImageReadAuthorizer();

    private Image privateImage() {
        return new Image(ImageConstants.NUMBER, ImageConstants.NAME, ImageConstants.DESCRIPTION, ImageConstants.KEY,
            ImageConstants.PNG_MEDIA_TYPE, ImageConstants.DATA.length, false, Optional.empty());
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("A private image is accepted with read permission")
    void testPrivateImageAccepted() {
        SecurityContextHolder.getContext()
            .setAuthentication(new TestingAuthenticationToken("user", "password", "IMAGE:READ"));

        Assertions.assertThatCode(() -> authorizer.checkCanRead(privateImage()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("A private image is rejected without read permission")
    void testPrivateImageDenied() {
        Assertions.assertThatThrownBy(() -> authorizer.checkCanRead(privateImage()))
            .isInstanceOf(AccessDeniedException.class);
    }
}
