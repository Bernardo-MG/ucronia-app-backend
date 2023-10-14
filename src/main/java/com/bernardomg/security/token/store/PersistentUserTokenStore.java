/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.security.token.store;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.bernardomg.security.token.exception.ConsumedTokenException;
import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.token.persistence.repository.UserTokenRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersistentUserTokenStore implements UserTokenStore {

    /**
     * Token scope.
     */
    private final String              tokenScope;

    private final UserTokenRepository userTokenRepository;

    /**
     * Token validity duration. This is how long the token is valid, starting on the time it is created.
     */
    private final Duration            validity;

    public PersistentUserTokenStore(@NonNull final UserTokenRepository tRepository, @NonNull final String token,
            @NonNull final Duration valid) {
        super();

        userTokenRepository = Objects.requireNonNull(tRepository);
        validity = Objects.requireNonNull(valid);
        tokenScope = Objects.requireNonNull(token);
    }

    @Override
    public final void consumeToken(final String token) {
        final Optional<PersistentUserToken> read;
        final PersistentUserToken           persistentToken;

        read = userTokenRepository.findOneByToken(token);

        if (!read.isPresent()) {
            log.error("Token missing: {}", token);
            throw new MissingTokenException(token);
        }

        persistentToken = read.get();
        if (persistentToken.isConsumed()) {
            log.warn("Token already consumed: {}", token);
            throw new ConsumedTokenException(token);
        }

        persistentToken.setConsumed(true);
        userTokenRepository.save(persistentToken);
        log.debug("Consumed token {}", token);
    }

    @Override
    public final String createToken(final Long userId, final String username) {
        final PersistentUserToken persistentToken;
        final LocalDateTime       creation;
        final LocalDateTime       expiration;
        final String              tokenCode;

        expiration = LocalDateTime.now()
            .plus(validity);

        creation = LocalDateTime.now();

        tokenCode = UUID.randomUUID()
            .toString();

        persistentToken = new PersistentUserToken();
        persistentToken.setUserId(userId);
        persistentToken.setScope(tokenScope);
        persistentToken.setCreationDate(creation);
        persistentToken.setToken(tokenCode);
        persistentToken.setConsumed(false);
        persistentToken.setRevoked(false);
        persistentToken.setExpirationDate(expiration);

        userTokenRepository.save(persistentToken);

        log.debug("Created token for {} with scope {}", username, tokenScope);

        return tokenCode;
    }

    @Override
    public final boolean exists(final String token) {
        return userTokenRepository.existsByTokenAndScope(token, tokenScope);
    }

    @Override
    public final String getUsername(final String token) {
        final Optional<String> username;

        // TODO: Shouldn't this receive the scope?
        username = userTokenRepository.findUsernameByToken(token);

        if (username.isEmpty()) {
            throw new InvalidTokenException(token);
        }

        return username.get();
    }

    @Override
    public final boolean isValid(final String token) {
        final Optional<PersistentUserToken> read;
        final PersistentUserToken           entity;
        final Boolean                       valid;

        // TODO: Use the token service to verify it
        // TODO: Check scope

        read = userTokenRepository.findOneByToken(token);
        if (read.isPresent()) {
            entity = read.get();
            if (!tokenScope.equals(entity.getScope())) {
                // scope mismatch
                valid = false;
                log.warn("Expected scope {}, but the token is for {}", tokenScope, entity.getScope());
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
            } else if (LocalDateTime.now()
                .isAfter(entity.getExpirationDate())) {
                // Expired
                // It isn't a valid token
                valid = false;
                log.warn("Expired token: {}", token);
            } else {
                // Not expired
                // Verifies the expiration date is after the current date
                valid = entity.getExpirationDate()
                    .isAfter(LocalDateTime.now());
            }
        } else {
            log.warn("Token not registered: {}", token);
            valid = false;
        }

        return valid;
    }

    @Override
    public final void revokeExistingTokens(final Long userId) {
        final Collection<PersistentUserToken> notRevoked;

        notRevoked = userTokenRepository.findAllNotRevokedByUserIdAndScope(userId, tokenScope);
        notRevoked.forEach(t -> t.setRevoked(true));

        userTokenRepository.saveAll(notRevoked);

        log.debug("Revoked all existing tokens with scope {} for {}", tokenScope, userId);
    }

}
