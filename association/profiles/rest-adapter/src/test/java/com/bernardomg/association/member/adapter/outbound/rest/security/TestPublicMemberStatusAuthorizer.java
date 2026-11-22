
package com.bernardomg.association.member.adapter.outbound.rest.security;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicMemberStatusAuthorizer - checkCanRead")
class TestPublicMemberStatusAuthorizer {

    @InjectMocks
    private PublicMemberStatusAuthorizer authorizer;

    @Mock
    private ResourcePermissionEvaluator  permissionEvaluator;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("When active member status is checked, then no permission check is made")
    void testActiveMembersCanBeRead() {
        // WHEN
        authorizer.checkCanRead(MemberStatus.ACTIVE);

        // THEN
        verifyNoInteractions(permissionEvaluator);
    }

    @Test
    @DisplayName("When inactive member read permission is granted, then status access is allowed")
    void testInactiveMembersCanBeReadWhenAuthorized() {
        final Authentication authentication;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "INACTIVE_MEMBER", Actions.READ)).willReturn(true);

        // WHEN
        authorizer.checkCanRead(MemberStatus.INACTIVE);

        // THEN
        verify(permissionEvaluator).isAuthorized(authentication, "INACTIVE_MEMBER", Actions.READ);
    }

    @Test
    @DisplayName("When inactive member read permission is denied, then access is rejected")
    void testInactiveMembersCannotBeReadWhenUnauthorized() {
        final Authentication   authentication;
        final ThrowingCallable action;

        // GIVEN
        authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "INACTIVE_MEMBER", Actions.READ)).willReturn(false);

        // WHEN
        action = () -> authorizer.checkCanRead(MemberStatus.INACTIVE);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Rejects inactive member status access when permission is denied")
            .isInstanceOf(org.springframework.security.access.AccessDeniedException.class);
        verify(permissionEvaluator).isAuthorized(authentication, "INACTIVE_MEMBER", Actions.READ);
    }

}
