
package com.bernardomg.security.token.store;

import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersistentTokenStore implements TokenStore {

    private final TokenRepository tokenRepository;

    private final Integer         validity;

    public PersistentTokenStore(@NonNull final TokenRepository tRepository, @NonNull final Integer valid) {
        super();

        tokenRepository = tRepository;
        validity = valid;
    }

    @Override
    public final void consumeToken(final String token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;

        read = tokenRepository.findOneByToken(token);

        if (read.isPresent()) {
            if (read.get()
                .isConsumed()) {
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

        expiration = Calendar.getInstance();
        expiration.add(Calendar.MILLISECOND, validity);

        creation = Calendar.getInstance();

        tokenCode = UUID.randomUUID()
            .toString();

        persistentToken = new PersistentToken();
        persistentToken.setUserId(userId);
        persistentToken.setScope(scope);
        persistentToken.setCreationDate(creation);
        persistentToken.setToken(tokenCode);
        persistentToken.setConsumed(false);
        persistentToken.setRevoked(false);
        persistentToken.setExpirationDate(expiration);

        tokenRepository.save(persistentToken);

        log.debug("Created token for {} with scope {}", username, scope);

        return tokenCode;
    }

    @Override
    public final boolean exists(final String token, final String scope) {
        return tokenRepository.existsByTokenAndScope(token, scope);
    }

    @Override
    public final String getUsername(final String token) {
        final Optional<String> username;

        username = tokenRepository.findUsernameByToken(token);

        if (username.isEmpty()) {
            throw new InvalidTokenException(token);
        }

        return username.get();
    }

    @Override
    public final boolean isValid(final String token, final String scope) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   valid;

        // TODO: Use the token service to verify it
        // TODO: Check scope

        read = tokenRepository.findOneByToken(token);
        if (read.isPresent()) {
            entity = read.get();
            if (!scope.equals(entity.getScope())) {
                // scope mismatch
                valid = false;
                log.warn("Expected scope {}, but the token is for {}", scope, entity.getScope());
            } else if (entity.isConsumed()) {
                // Consumed
                // It isn't a valid token
                valid = false;
                log.warn("Consumed token: {}", token);
            } else if (entity.isRevoked()) {
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

    @Override
    public final void revokeExistingTokens(final Long userId, final String scope) {
        final Collection<PersistentToken> notRevoked;

        notRevoked = tokenRepository.findAllNotRevokedByUserIdAndScope(userId, scope);
        notRevoked.forEach(t -> t.setRevoked(true));

        tokenRepository.saveAll(notRevoked);

        log.debug("Revoked all existing tokens with scope {} for {}", scope, userId);
    }

}
