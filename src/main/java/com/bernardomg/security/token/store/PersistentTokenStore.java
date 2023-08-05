
package com.bernardomg.security.token.store;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.security.core.token.TokenService;

import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersistentTokenStore implements TokenStore {

    private final TokenRepository tokenRepository;

    private final TokenService    tokenService;

    private final Integer         validity;

    public PersistentTokenStore(@NonNull final TokenRepository tRepository, @NonNull final TokenService tService,
            @NonNull final Integer valid) {
        super();

        tokenRepository = tRepository;
        tokenService = tService;
        validity = valid;
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
                log.debug("Consumed token {}", token);
            }
        } else {
            log.warn("Token not registered: {}", token);
        }
    }

    @Override
    public final String createToken(final Long userId, final String username, final String scope) {
        final PersistentToken persistentToken;
        final Calendar        creation;
        final Calendar        expiration;
        final String          tokenCode;

        // TODO: This should be configurable
        expiration = Calendar.getInstance();
        expiration.add(Calendar.MILLISECOND, validity);

        creation = Calendar.getInstance();

        // TODO: Shouldn't this include the scope?
        tokenCode = tokenService.allocateToken(username)
            .getKey();

        persistentToken = new PersistentToken();
        persistentToken.setUserId(userId);
        persistentToken.setScope(scope);
        persistentToken.setCreationDate(creation);
        persistentToken.setToken(tokenCode);
        persistentToken.setConsumed(false);
        persistentToken.setRevoked(false);
        persistentToken.setExpirationDate(expiration);

        tokenRepository.save(persistentToken);

        return tokenCode;
    }

    @Override
    public final boolean exists(final String token, final String scope) {
        return tokenRepository.existsByTokenAndScope(token, scope);
    }

    @Override
    public final String getUsername(final String token) {
        final String username;

        try {
            username = tokenService.verifyToken(token)
                .getExtendedInformation();
        } catch (final Exception e) {
            throw new InvalidTokenException(token);
        }

        return username;
    }

    @Override
    public final Boolean isValid(final String token, final String scope) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   valid;

        // TODO: Use the token service to verify it

        read = tokenRepository.findOneByToken(token);
        if (read.isPresent()) {
            entity = read.get();
            if (!scope.equals(entity.getScope())) {
                // scope mismatch
                valid = false;
                log.warn("Expected scope {}, but the token is for {}", scope, entity.getScope());
            } else if (entity.getConsumed()) {
                // Consumed
                // It isn't a valid token
                valid = false;
                log.warn("Consumed token: {}", token);
            } else if (entity.getRevoked()) {
                // Revoked
                // It isn't a valid token
                valid = false;
                log.warn("Revoked token: {}", token);
            } else if (Calendar.getInstance()
                .after(entity.getExpirationDate())) {
                // Expired
                // It isn't a valid token
                valid = false;
                log.warn("Expired token: {}", token);
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
