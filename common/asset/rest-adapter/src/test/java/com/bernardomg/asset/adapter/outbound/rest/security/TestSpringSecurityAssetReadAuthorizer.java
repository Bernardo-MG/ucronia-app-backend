/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.asset.adapter.outbound.rest.security;

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

import com.bernardomg.asset.test.configuration.factory.Assets;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringSecurityAssetReadAuthorizer")
class TestSpringSecurityAssetReadAuthorizer {

    private AssetReadAuthorizer         authorizer;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @BeforeEach
    void setUp() {
        authorizer = new SpringSecurityAssetReadAuthorizer(permissionEvaluator);
    }

    @Test
    @DisplayName("Private assets can be read when permission is granted")
    void testCanReadPrivateAssets_Authorized() {
        final Authentication authentication;
        final boolean        authorized;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "ASSETS", Actions.READ)).willReturn(true);

        // WHEN
        authorized = authorizer.canReadPrivate();

        // THEN
        Assertions.assertThat(authorized)
            .isTrue();
    }

    @Test
    @DisplayName("Private assets can't be read when permission is denied")
    void testCanReadPrivateAssets_Denied() {
        final Authentication authentication;
        final boolean        authorized;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "ASSETS", Actions.READ)).willReturn(false);

        // WHEN
        authorized = authorizer.canReadPrivate();

        // THEN
        Assertions.assertThat(authorized)
            .isFalse();
    }

    @Test
    @DisplayName("A private asset is accepted with read permission")
    void testPrivateAssetAccepted() {
        final Authentication authentication;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "ASSETS", Actions.READ)).willReturn(true);

        // WHEN + THEN
        Assertions.assertThatCode(() -> authorizer.checkCanRead(Assets.privateAccess()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("A private asset is rejected without read permission")
    void testPrivateAssetDenied() {
        final Authentication authentication;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "ASSETS", Actions.READ)).willReturn(false);

        // WHEN + THEN
        Assertions.assertThatThrownBy(() -> authorizer.checkCanRead(Assets.privateAccess()))
            .isInstanceOf(AccessDeniedException.class)
            .hasMessage("No permissions for reading private assets");
    }

    @Test
    @DisplayName("A public asset is accepted without checking permissions")
    void testPublicAssetAccepted() {
        // WHEN + THEN
        Assertions.assertThatCode(() -> authorizer.checkCanRead(Assets.publicAccess()))
            .doesNotThrowAnyException();
    }

}
