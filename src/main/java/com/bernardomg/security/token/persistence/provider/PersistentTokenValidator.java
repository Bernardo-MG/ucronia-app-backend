
package com.bernardomg.security.token.persistence.provider;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.provider.TokenValidator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PersistentTokenValidator implements TokenValidator {

    private final TokenRepository tokenRepository;

    private final TokenService    tokenService;

    public PersistentTokenValidator(@NonNull final TokenRepository tRepository, @NonNull final TokenService tService) {
        super();

        tokenRepository = tRepository;
        tokenService = tService;
    }

    @Override
    public final String getSubject(final String token) {
        final Token  parsedToken;
        final String subject;

        if (tokenRepository.existsByToken(token)) {
            parsedToken = tokenService.verifyToken(token);
            subject = parsedToken.getExtendedInformation();
        } else {
            log.warn("Token {} isn't registered", token);
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
            log.warn("Token {} isn't registered", token);
            expired = true;
        }

        return expired;
    }

}
