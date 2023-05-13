
package com.bernardomg.security.token.persistence.provider;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.provider.TokenProcessor;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersistentTokenProcessor implements TokenProcessor {

    private final TokenRepository tokenRepository;

    private final TokenService    tokenService;

    public PersistentTokenProcessor(@NonNull final TokenRepository tRepository, @NonNull final TokenService tService) {
        super();

        tokenRepository = tRepository;
        tokenService = tService;
    }

    @Override
    public final void closeToken(final String token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;

        read = tokenRepository.findOneByToken(token);

        if (read.isPresent()) {
            if (read.get()
                .getExpired()) {
                log.warn("Token already expired: {}", token);
            } else {
                entity = read.get();
                entity.setExpired(true);
                tokenRepository.save(entity);
            }
        } else {
            log.warn("Token not registered: {}", token);
        }
    }

    @Override
    public final Optional<Token> decode(final String token) {
        final Token  parsedToken;
        final String subject;

        if (tokenRepository.existsByToken(token)) {
            parsedToken = tokenService.verifyToken(token);
            subject = parsedToken.getExtendedInformation();
        } else {
            log.warn("Token not registered: {}", token);
            parsedToken = null;
        }

        return Optional.of(parsedToken);
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
        token.setExpirationDate(expiration);

        tokenRepository.save(token);

        return uniqueID;
    }

    @Override
    public final Boolean hasExpired(final String token) {
        final Optional<PersistentToken> read;
        final PersistentToken           entity;
        final Boolean                   expired;

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
            log.warn("Token not registered: {}", token);
            expired = true;
        }

        return expired;
    }

}
