/** The MIT License (MIT). Copyright (c) 2022-2026 Bernardo Martínez Garrido. */

package com.bernardomg.file.adapter.outbound.rest.security;

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

import com.bernardomg.file.test.configuration.factory.Files;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringSecurityFileReadAuthorizer")
class TestSpringSecurityFileReadAuthorizer {

    private FileReadAuthorizer          authorizer;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @BeforeEach
    void setUp() {
        authorizer = new SpringSecurityFileReadAuthorizer(permissionEvaluator);
    }

    @Test
    @DisplayName("Private files can be read when permission is granted")
    void testCanReadPrivateFiles_Authorized() {
        final Authentication authentication;
        final boolean        authorized;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "FILE", Actions.READ)).willReturn(true);

        // WHEN
        authorized = authorizer.canReadPrivateFiles();

        // THEN
        Assertions.assertThat(authorized)
            .isTrue();
    }

    @Test
    @DisplayName("Private files can't be read when permission is denied")
    void testCanReadPrivateFiles_Denied() {
        final Authentication authentication;
        final boolean        authorized;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "FILE", Actions.READ)).willReturn(false);

        // WHEN
        authorized = authorizer.canReadPrivateFiles();

        // THEN
        Assertions.assertThat(authorized)
            .isFalse();
    }

    @Test
    @DisplayName("A private file is accepted with read permission")
    void testPrivateFileAccepted() {
        final Authentication authentication;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "FILE", Actions.READ)).willReturn(true);

        // WHEN + THEN
        Assertions.assertThatCode(() -> authorizer.checkCanRead(Files.privateAccess()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("A private file is rejected without read permission")
    void testPrivateFileDenied() {
        final Authentication authentication;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "FILE", Actions.READ)).willReturn(false);

        // WHEN + THEN
        Assertions.assertThatThrownBy(() -> authorizer.checkCanRead(Files.privateAccess()))
            .isInstanceOf(AccessDeniedException.class)
            .hasMessage("No permissions for reading private files");
    }

    @Test
    @DisplayName("A public file is accepted without checking permissions")
    void testPublicFileAccepted() {
        // WHEN + THEN
        Assertions.assertThatCode(() -> authorizer.checkCanRead(Files.publicAccess()))
            .doesNotThrowAnyException();
    }

}
