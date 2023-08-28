
package com.bernardomg.security.token.service;

import java.util.Collection;
import java.util.Objects;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultTokenCleanUpService implements TokenCleanUpService {

    private final TokenRepository tokenRepository;

    public DefaultTokenCleanUpService(final TokenRepository respository) {
        super();

        tokenRepository = Objects.requireNonNull(respository);
    }

    @Override
    public final void cleanUpTokens() {
        final Collection<PersistentToken> tokens;

        // Expiration date before now
        // Revoked
        // Consumed
        tokens = tokenRepository.findAllFinished();

        log.info("Removing {} finished tokens", tokens.size());

        tokenRepository.deleteAll(tokens);
    }

}
