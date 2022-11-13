
package com.bernardomg.security.token.service;

import java.util.Calendar;
import java.util.Optional;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

import lombok.NonNull;

public class OneUseTokenService implements TokenService {

    private final TokenRepository tokenRepository;

    public OneUseTokenService(@NonNull final TokenRepository tRepository) {
        super();

        tokenRepository = tRepository;
    }

    @Override
    public final Boolean verifyToken(final String Token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   valid;

        // TODO: Move to its own service

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

}
