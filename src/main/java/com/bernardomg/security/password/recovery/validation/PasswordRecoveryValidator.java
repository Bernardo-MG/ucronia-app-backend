
package com.bernardomg.security.password.recovery.validation;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PasswordRecoveryValidator extends AbstractValidator<PasswordValidationData> {

    public PasswordRecoveryValidator() {
        super();
    }

    @Override
    protected final void checkRules(final PasswordValidationData data, final Collection<FieldFailure> failures) {
        final FieldFailure   failure;
        final Authentication auth;
        final String         sessionUser;
        final PersistentUser user;

        if (!data.getUser()
            .isPresent()) {
            log.warn("The email {} isn't registered", data.getEmail());
            failure = FieldFailure.of("email", "invalid", data.getEmail());
            failures.add(failure);
        } else {
            // TODO: This process will be started by users not authenticated
            auth = SecurityContextHolder.getContext()
                .getAuthentication();
            if (auth != null) {
                sessionUser = auth.getName();
            } else {
                sessionUser = "";
            }

            user = data.getUser()
                .get();
            if (!user.getUsername()
                .equals(sessionUser)) {
                log.error("The user {} tried to change the password for {}", sessionUser, user.getUsername());
                failure = FieldFailure.of("email", "invalid", data.getEmail());
                failures.add(failure);
            }
        }
    }

}
