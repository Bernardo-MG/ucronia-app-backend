
package com.bernardomg.asset.adapter.outbound.rest.security;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
@DisplayName("SpringSecurityAssetReadAuthorizer - canReadPrivate")
class TestSpringSecurityAssetReadAuthorizer {

    @InjectMocks
    private SpringSecurityAssetReadAuthorizer authorizer;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("When read permission is granted, then private assets can be read")
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
            .as("Private assets can be read when permission is granted")
            .isTrue();
    }

    @Test
    @DisplayName("When read permission is denied, then private assets cannot be read")
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
            .as("Private assets can't be read when permission is denied")
            .isFalse();
    }

    @Test
    @DisplayName("When read permission is granted, then a private asset is accepted")
    void testPrivateAssetAccepted() {
        final Authentication   authentication;
        final ThrowingCallable action;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "ASSETS", Actions.READ)).willReturn(true);

        // WHEN
        action = () -> authorizer.checkCanRead(Assets.privateAccess());

        // THEN
        Assertions.assertThatCode(action)
            .as("private asset is accepted")
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("When read permission is missing, then a private asset is rejected")
    void testPrivateAssetDenied() {
        final Authentication   authentication;
        final ThrowingCallable action;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "ASSETS", Actions.READ)).willReturn(false);

        // WHEN
        action = () -> authorizer.checkCanRead(Assets.privateAccess());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("private asset is rejected")
            .isInstanceOf(AccessDeniedException.class)
            .hasMessage("No permissions for reading private assets");
    }

    @Test
    @DisplayName("When a public asset is requested, then it is accepted without checking permissions")
    void testPublicAssetAccepted() {
        final ThrowingCallable action;

        // WHEN
        action = () -> authorizer.checkCanRead(Assets.publicAccess());

        // THEN
        Assertions.assertThatCode(action)
            .as("public asset is accepted")
            .doesNotThrowAnyException();
    }

}
