
package com.bernardomg.image.adapter.outbound.rest.security;

import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.image.domain.model.Image;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

public final class SpringSecurityImageReadAuthorizer implements ImageReadAuthorizer {

    private static final String               RESOURCE = "IMAGE";

    private final ResourcePermissionEvaluator permissionEvaluator;

    public SpringSecurityImageReadAuthorizer(final ResourcePermissionEvaluator permissionEvaluator) {
        super();

        this.permissionEvaluator = Objects.requireNonNull(permissionEvaluator);
    }

    @Override
    public boolean canReadPrivateImages() {
        final Authentication authentication;

        authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        return permissionEvaluator.isAuthorized(authentication, RESOURCE, Actions.READ);
    }

    @Override
    public void checkCanRead(final Image image) {
        if (!image.publicAccess() && !canReadPrivateImages()) {
            throw new AccessDeniedException("No permissions for reading private images");
        }
    }

}
