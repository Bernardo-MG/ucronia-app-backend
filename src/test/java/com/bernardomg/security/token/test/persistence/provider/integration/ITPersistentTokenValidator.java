
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.token.persistence.provider.PersistentTokenValidator;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.provider.TokenValidator;

@IntegrationTest
@DisplayName("PersistentTokenValidator")
public class ITPersistentTokenValidator {

    private final TokenValidator validator;

    @Autowired
    public ITPersistentTokenValidator(final TokenRepository tokenRepository, final TokenService tokenService) {
        super();

        validator = new PersistentTokenValidator(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("Retrieves the subject from a token")
    public final void testGetSubject() {
        final String token;
        final String subject;

        token = "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y";

        subject = validator.getSubject(token);

        Assertions.assertNotEquals("admin", subject);
    }

    @Test
    @DisplayName("A token after the expiration date has expired")
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    public final void testHasExpired_AfterExpirationDate() {
        final String  token;
        final Boolean expired;

        token = "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y";

        expired = validator.hasExpired(token);

        Assertions.assertTrue(expired);
    }

    @Test
    @DisplayName("A expired token has expired")
    @Sql({ "/db/queries/security/token/expired.sql" })
    public final void testHasExpired_Expired() {
        final String  token;
        final Boolean expired;

        token = "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y";

        expired = validator.hasExpired(token);

        Assertions.assertTrue(expired);
    }

    @Test
    @DisplayName("A valid token has not expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testHasExpired_Valid() {
        final String  token;
        final Boolean expired;

        token = "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y";

        expired = validator.hasExpired(token);

        Assertions.assertFalse(expired);
    }

}
