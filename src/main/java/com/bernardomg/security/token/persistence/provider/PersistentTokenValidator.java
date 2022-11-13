
package com.bernardomg.security.token.persistence.provider;

import java.util.Calendar;
import java.util.Optional;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.provider.TokenValidator;

import lombok.NonNull;

public final class PersistentTokenValidator implements TokenValidator {

    private final TokenRepository tokenRepository;

    public PersistentTokenValidator(@NonNull final TokenRepository tRepository) {
        super();

        tokenRepository = tRepository;
    }

    @Override
    public final String getSubject(final String token) {
        final Optional<PersistentToken> read;
        final String                    subject;

        read = tokenRepository.findOneByToken(token);
        if (read.isPresent()) {
            subject = read.get()
                .getToken();
        } else {
            subject = "";
        }

        return subject;
    }

    @Override
    public final Boolean hasExpired(final String token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   expired;

        // TODO: Move to its own service

        read = tokenRepository.findOneByToken(token);
        if (read.isPresent()) {
            entity = read.get();
            if (entity.getExpired()) {
                // Expired
                // It isn't a valid token
                expired = true;
            } else {
                // Not expired
                // Verifies the expiration date is after the current date
                expired = Calendar.getInstance()
                    .after(entity.getExpirationDate());
            }
        } else {
            expired = true;
        }

        return expired;
    }

}
