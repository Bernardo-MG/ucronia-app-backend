
package com.bernardomg.file.adapter.outbound.rest.security;

import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.file.domain.model.File;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

public final class SpringSecurityFileReadAuthorizer implements FileReadAuthorizer {

    private static final String               RESOURCE = "FILE";

    private final ResourcePermissionEvaluator permissionEvaluator;

    public SpringSecurityFileReadAuthorizer(final ResourcePermissionEvaluator permissionEvaluator) {
        super();

        this.permissionEvaluator = Objects.requireNonNull(permissionEvaluator);
    }

    @Override
    public boolean canReadPrivateFiles() {
        final Authentication authentication;

        authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        return permissionEvaluator.isAuthorized(authentication, RESOURCE, Actions.READ);
    }

    @Override
    public void checkCanRead(final File file) {
        if (!file.publicAccess() && !canReadPrivateFiles()) {
            throw new AccessDeniedException("No permissions for reading private files");
        }
    }

}
