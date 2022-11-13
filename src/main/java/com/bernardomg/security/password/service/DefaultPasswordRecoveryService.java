
package com.bernardomg.security.password.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.validation.exception.ValidationException;

import lombok.NonNull;

public final class DefaultPasswordRecoveryService implements PasswordRecoveryService {

    private final SecurityEmailSender mailSender;

    private final UserRepository      repository;

    private final TokenRepository     tokenRepository;

    /**
     * User details service, to find and validate users.
     */
    private final UserDetailsService  userDetailsService;

    public DefaultPasswordRecoveryService(@NonNull final UserRepository repo,
            @NonNull final UserDetailsService userDetsService, @NonNull final SecurityEmailSender mSender,
            @NonNull final TokenRepository tRepository) {
        super();

        repository = repo;
        userDetailsService = userDetsService;
        mailSender = mSender;
        tokenRepository = tRepository;
    }

    @Override
    public final Boolean startPasswordRecovery(final String email) {
        final Optional<PersistentUser> user;
        final Failure                  error;
        final UserDetails              details;
        final Boolean                  valid;

        user = repository.findOneByEmail(email);
        if (!user.isPresent()) {
            error = FieldFailure.of("error.email.notExisting", "roleForm", "memberId", email);
            throw new ValidationException(Arrays.asList(error));
        }

        details = userDetailsService.loadUserByUsername(user.get()
            .getUsername());

        valid = isValid(details);
        if (valid) {
            registerToken();

            // TODO: Send token
            mailSender.sendPasswordRecoveryEmail(user.get()
                .getEmail());
        }

        return valid;
    }

    /**
     * Checks if the user is valid. This means it has no flag marking it as not usable.
     *
     * @param userDetails
     *            user the check
     * @return {@code true} if the user is valid, {@code false} otherwise
     */
    private final Boolean isValid(final UserDetails userDetails) {
        return userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()
                && userDetails.isCredentialsNonExpired() && userDetails.isEnabled();
    }

    private final void registerToken() {
        final PersistentToken token;
        final Calendar        expiration;
        final String          uniqueID;

        expiration = Calendar.getInstance();
        expiration.add(Calendar.DATE, 1);

        uniqueID = UUID.randomUUID()
            .toString();

        token = new PersistentToken();
        token.setToken(uniqueID);
        token.setExpired(false);
        token.setExpirationDate(expiration);

        tokenRepository.save(token);
    }

}
