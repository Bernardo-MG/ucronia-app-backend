
package com.bernardomg.association.fee.usecase.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SpringUserSessionProvider implements UserSessionProvider {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(SpringUserSessionProvider.class);

    @Override
    public final String getUsername() {
        final Authentication authentication;
        final UserDetails    userDetails;

        log.info("Getting all the fees for the user in session");

        authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)
                || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user in session");
        }

        userDetails = (UserDetails) authentication.getPrincipal();

        return userDetails.getUsername();
    }

}
