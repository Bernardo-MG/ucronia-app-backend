
package com.bernardomg.asset.adapter.outbound.rest.security;

import java.util.Objects;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.asset.domain.model.Asset;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;

public final class SpringSecurityAssetReadAuthorizer implements AssetReadAuthorizer {

    private final ResourcePermissionEvaluator permissionEvaluator;

    private final String                      resource;

    public SpringSecurityAssetReadAuthorizer(final ResourcePermissionEvaluator permissionEval,
            final String resourceName) {
        super();

        permissionEvaluator = Objects.requireNonNull(permissionEval);
        resource = Objects.requireNonNull(resourceName);
    }

    @Override
    public boolean canReadPrivate() {
        final Authentication authentication;

        authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        return permissionEvaluator.isAuthorized(authentication, resource, Actions.READ);
    }

    @Override
    public void checkCanRead(final Asset asset) {
        if (!asset.publicAccess() && !canReadPrivate()) {
            throw new AccessDeniedException("No permissions for reading private assets");
        }
    }

}
