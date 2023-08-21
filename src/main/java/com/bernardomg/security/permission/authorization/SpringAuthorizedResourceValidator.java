
package com.bernardomg.security.permission.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.security.permission.userdetails.ResourceActionGrantedAuthority;

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
            log.debug("Can user {} apply action {} to resource {}: {}", authentication.getName(), action, resource,
                authorized);
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
