
package com.bernardomg.image.adapter.outbound.rest.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.image.domain.model.Image;

public final class SpringSecurityImageReadAuthorizer implements ImageReadAuthorizer {

    private static final String READ_AUTHORITY = "IMAGE:READ";

    @Override
    public boolean canReadPrivateImages() {
        final Authentication authentication;

        authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        return (authentication != null) && authentication.isAuthenticated() && authentication.getAuthorities()
            .stream()
            .anyMatch(authority -> READ_AUTHORITY.equals(authority.getAuthority()));
    }

    @Override
    public void checkCanRead(final Image image) {
        if (!image.publicAccess() && !canReadPrivateImages()) {
            throw new AccessDeniedException("IMAGE:READ is required to read a private image");
        }
    }

}
