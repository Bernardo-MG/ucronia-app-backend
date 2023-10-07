
package com.bernardomg.security.auth.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.security.auth.springframework.userdetails.ResourceActionGrantedAuthority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.debug("Missing authentication object");
            authorized = false;
        } else if (authentication.isAuthenticated()) {
            authorized = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> isValid(a, resource, action));
            log.debug("Authorized user {} against resource {} with action {}: {}", authentication.getName(), resource,
                action, authorized);
        } else {
            log.debug("User {} is not authenticated", authentication.getName());
            authorized = false;
        }

        return authorized;
    }

    private final boolean isValid(final GrantedAuthority authority, final String resource, final String action) {
        final boolean valid;

        if (authority instanceof final ResourceActionGrantedAuthority resourceAuthority) {
            valid = resourceAuthority.getResource()
                .equals(resource)
                    && resourceAuthority.getAction()
                        .equals(action);
        } else {
            valid = false;
        }

        return valid;
    }

}
