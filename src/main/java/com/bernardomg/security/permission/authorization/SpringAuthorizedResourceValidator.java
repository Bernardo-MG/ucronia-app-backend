
package com.bernardomg.security.permission.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SpringAuthorizedResourceValidator implements AuthorizedResourceValidator {

    public SpringAuthorizedResourceValidator() {
        super();
    }

    @Override
    public final boolean isAuthorized(final String resource, final String action) {
        final Authentication authentication;
        final boolean        authorized;

        // TODO: Check before casting
        authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if (authentication == null) {
            authorized = false;
        } else if (authentication.isAuthenticated()) {
            authorized = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> (resource + ":" + action).equals(a.getAuthority()));
        } else {
            authorized = false;
        }

        return authorized;
    }

}
