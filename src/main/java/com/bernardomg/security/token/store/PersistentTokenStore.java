
package com.bernardomg.security.token.store;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersistentTokenStore implements TokenStore<Token> {

    private final TokenRepository tokenRepository;

    private final TokenService    tokenService;

    public PersistentTokenStore(@NonNull final TokenRepository tRepository, @NonNull final TokenService tService) {
        super();

        tokenRepository = tRepository;
        tokenService = tService;
    }

    @Override
    public final void consumeToken(final String token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;

        read = tokenRepository.findOneByToken(token);

        if (read.isPresent()) {
            if (read.get()
                .getConsumed()) {
                log.warn("Token already consumed: {}", token);
            } else {
                entity = read.get();
                entity.setConsumed(true);
                tokenRepository.save(entity);
            }
        } else {
            log.warn("Token not registered: {}", token);
        }
    }

    @Override
    public final Token decode(final String token) {
        return tokenService.verifyToken(token);
    }

    @Override
    public final boolean exists(final String token) {
        return tokenRepository.existsByToken(token);
    }

    @Override
    public final String generateToken(final String subject) {
        final PersistentToken token;
        final Calendar        expiration;
        final String          uniqueID;

        expiration = Calendar.getInstance();
        expiration.add(Calendar.DATE, 1);

        uniqueID = tokenService.allocateToken(subject)
            .getKey();

        token = new PersistentToken();
        token.setToken(uniqueID);
        token.setExpired(false);
        token.setConsumed(false);
        token.setExpirationDate(expiration);

        tokenRepository.save(token);

        return uniqueID;
    }

    @Override
    public final Boolean isValid(final String token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   valid;

        read = tokenRepository.findOneByToken(token);
        if (read.isPresent()) {
            entity = read.get();
            if (entity.getExpired()) {
                // Expired
                // It isn't a valid token
                valid = false;
                log.debug("Expired token: {}", token);
            } else if (entity.getConsumed()) {
                // Consumed
                // It isn't a valid token
                valid = false;
                log.debug("Consumed token: {}", token);
            } else {
                // Not expired
                // Verifies the expiration date is after the current date
                valid = entity.getExpirationDate()
                    .after(Calendar.getInstance());
            }
        } else {
            log.warn("Token not registered: {}", token);
            valid = false;
        }

        return valid;
    }

}
