
package com.bernardomg.security.token.store;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.security.core.token.TokenService;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersistentTokenStore implements TokenStore {

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
    public final boolean exists(final String token, final String purpose) {
        return tokenRepository.existsByTokenAndPurpose(token, purpose);
    }

    @Override
    public final String generateToken(final Long userId, final String username, final String purpose) {
        final PersistentToken persistentToken;
        final Calendar        creation;
        final Calendar        expiration;
        final String          tokenCode;

        // TODO: This should be configurable
        expiration = Calendar.getInstance();
        expiration.add(Calendar.DATE, 1);

        creation = Calendar.getInstance();

        // TODO: Shouldn't this include the purpose?
        tokenCode = tokenService.allocateToken(username)
            .getKey();

        persistentToken = new PersistentToken();
        persistentToken.setUserId(userId);
        persistentToken.setPurpose(purpose);
        persistentToken.setCreationDate(creation);
        persistentToken.setToken(tokenCode);
        persistentToken.setConsumed(false);
        persistentToken.setExpirationDate(expiration);

        tokenRepository.save(persistentToken);

        return tokenCode;
    }

    @Override
    public final String getUsername(final String token) {
        return tokenService.verifyToken(token)
            .getExtendedInformation();
    }

    @Override
    public final Boolean isValid(final String token, final String purpose) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   valid;

        // TODO: Use the token service to verify it

        read = tokenRepository.findOneByToken(token);
        if (read.isPresent()) {
            entity = read.get();
            if (!purpose.equals(entity.getPurpose())) {
                // Purpose mismatch
                valid = false;
                log.warn("Expected purpose {}, but the token is for {}", purpose, entity.getPurpose());
            } else if (Calendar.getInstance()
                .after(entity.getExpirationDate())) {
                // Expired
                // It isn't a valid token
                valid = false;
                log.warn("Expired token: {}", token);
            } else if (entity.getConsumed()) {
                // Consumed
                // It isn't a valid token
                valid = false;
                log.warn("Consumed token: {}", token);
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
