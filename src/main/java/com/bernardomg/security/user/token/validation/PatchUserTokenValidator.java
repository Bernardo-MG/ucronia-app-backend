
package com.bernardomg.security.user.token.validation;

import java.time.LocalDateTime;
import java.util.Collection;

import com.bernardomg.security.user.token.model.UserTokenPartial;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PatchUserTokenValidator extends AbstractValidator<UserTokenPartial> {

    public PatchUserTokenValidator() {
        super();
    }

    @Override
    protected final void checkRules(final UserTokenPartial token, final Collection<FieldFailure> failures) {
        final LocalDateTime today;
        FieldFailure        failure;

        // Verify the expiration date is not before now
        today = LocalDateTime.now()
            .minusMinutes(2);
        if ((token.getExpirationDate() != null) && (token.getExpirationDate()
            .isBefore(today))) {
            log.error("Token expiration date {} is before now {}", token.getExpirationDate(), today);
            failure = FieldFailure.of("expirationDate", "beforeToday", token.getExpirationDate());
            failures.add(failure);
        }

        // Verify the token revoked flag is not cancelled
        if ((token.getRevoked() != null) && (!token.getRevoked())) {
            log.error("Reverting token revocation");
            failure = FieldFailure.of("revoked", "invalidValue", token.getRevoked());
            failures.add(failure);
        }
    }

}
