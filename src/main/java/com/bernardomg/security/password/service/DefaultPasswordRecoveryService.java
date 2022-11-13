
package com.bernardomg.security.password.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

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

    public DefaultPasswordRecoveryService(@NonNull final UserRepository repo,
            @NonNull final SecurityEmailSender mSender, @NonNull final TokenRepository tRepository) {
        super();

        repository = repo;
        mailSender = mSender;
        tokenRepository = tRepository;
    }

    @Override
    public final Boolean startPasswordRecovery(final String email) {
        final Optional<PersistentUser> user;
        final Failure                  error;

        user = repository.findOneByEmail(email);
        if (!user.isPresent()) {
            error = FieldFailure.of("error.email.notExisting", "roleForm", "memberId", email);
            throw new ValidationException(Arrays.asList(error));
        }

        registerToken();

        mailSender.sendPasswordRecoveryEmail(user.get()
            .getEmail());

        return true;
    }

    @Override
    public final Boolean verifyToken(final String Token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   valid;

        read = tokenRepository.findOneByToken(Token);
        if (read.isPresent()) {
            entity = read.get();
            if (entity.getExpired()) {
                // Expired
                // It isn't a valid token
                valid = false;
            } else {
                // Not expired
                // Verifies the expiration date is after the current date
                valid = entity.getExpirationDate()
                    .after(Calendar.getInstance());
            }
        } else {
            valid = false;
        }

        return valid;
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
