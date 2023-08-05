
package com.bernardomg.security.token.test.store.integration;

import java.util.Calendar;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - create token")
class ITPersistentTokenStoreCreateToken {

    private final PersistentTokenStore store;

    private final TokenRepository      tokenRepository;

    @Autowired
    public ITPersistentTokenStoreCreateToken(final TokenRepository tokenRepo, final TokenService tokenService) {
        super();

        store = new PersistentTokenStore(tokenRepo, tokenService, 1000);
        tokenRepository = tokenRepo;
    }

    @Test
    @DisplayName("After generating a token a new token is persisted")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testCreateToken_Persisted() {
        final long count;

        store.createToken(1l, "admin", "scope");

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("After generating a token said token data is persisted")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testCreateToken_PersistedData() {
        final PersistentToken token;
        final Calendar        lower;
        final Calendar        upper;

        lower = Calendar.getInstance();

        store.createToken(1l, "admin", "scope");

        token = tokenRepository.findAll()
            .iterator()
            .next();

        upper = Calendar.getInstance();
        upper.add(Calendar.SECOND, 1);

        Assertions.assertThat(token.getToken())
            .isNotNull();
        Assertions.assertThat(token.getScope())
            .isEqualTo("scope");
        Assertions.assertThat(token.getExpirationDate())
            .isGreaterThan(lower)
            .isLessThanOrEqualTo(upper);
        Assertions.assertThat(token.getConsumed())
            .isFalse();
        Assertions.assertThat(token.getRevoked())
            .isFalse();
    }

    @Test
    @DisplayName("After generating a token it returns said token")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testCreateToken_Return() {
        final String token;

        token = store.createToken(1l, "admin", "scope");

        Assertions.assertThat(token)
            .isNotNull();
    }

    @Test
    @DisplayName("Can generate tokens when the username doesn't match the user's")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testCreateToken_UserNameNotExisting() {
        final long count;

        // TODO: then, just take the username from the user id
        store.createToken(1l, "abc", "scope");

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("When generating a token for and invalid user id, then an exception is thrown")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testCreateToken_UserNotExisting() {
        final ThrowingCallable executable;

        executable = () -> {
            store.createToken(2l, "admin", "scope");
            tokenRepository.flush();
        };

        // TODO: Does this make sense? Throw a custom exception
        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(Exception.class);
    }

}
