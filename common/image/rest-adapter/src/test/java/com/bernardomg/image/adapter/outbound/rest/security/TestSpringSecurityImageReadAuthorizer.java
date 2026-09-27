/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.image.adapter.outbound.rest.security;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.image.test.configuration.factory.Images;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringSecurityImageReadAuthorizer")
class TestSpringSecurityImageReadAuthorizer {

    private ImageReadAuthorizer         authorizer;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @BeforeEach
    void setUp() {
        authorizer = new SpringSecurityImageReadAuthorizer(permissionEvaluator);
    }

    @Test
    @DisplayName("Private images can be read when permission is granted")
    void testCanReadPrivateImages_Authorized() {
        final Authentication authentication;
        final boolean        authorized;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "IMAGE", Actions.READ)).willReturn(true);

        // WHEN
        authorized = authorizer.canReadPrivateImages();

        // THEN
        Assertions.assertThat(authorized)
            .isTrue();
    }

    @Test
    @DisplayName("Private images can't be read when permission is denied")
    void testCanReadPrivateImages_Denied() {
        final Authentication authentication;
        final boolean        authorized;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "IMAGE", Actions.READ)).willReturn(false);

        // WHEN
        authorized = authorizer.canReadPrivateImages();

        // THEN
        Assertions.assertThat(authorized)
            .isFalse();
    }

    @Test
    @DisplayName("A private image is accepted with read permission")
    void testPrivateImageAccepted() {
        final Authentication authentication;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "IMAGE", Actions.READ)).willReturn(true);

        // WHEN + THEN
        Assertions.assertThatCode(() -> authorizer.checkCanRead(Images.privateAccess()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("A private image is rejected without read permission")
    void testPrivateImageDenied() {
        final Authentication authentication;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "IMAGE", Actions.READ)).willReturn(false);

        // WHEN + THEN
        Assertions.assertThatThrownBy(() -> authorizer.checkCanRead(Images.privateAccess()))
            .isInstanceOf(AccessDeniedException.class)
            .hasMessage("No permissions for reading private images");
    }

    @Test
    @DisplayName("A public image is accepted without checking permissions")
    void testPublicImageAccepted() {
        // WHEN + THEN
        Assertions.assertThatCode(() -> authorizer.checkCanRead(Images.publicAccess()))
            .doesNotThrowAnyException();
    }

}
