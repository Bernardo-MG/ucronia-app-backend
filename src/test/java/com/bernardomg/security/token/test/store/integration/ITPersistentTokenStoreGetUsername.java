
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - get username")
class ITPersistentTokenStoreGetUsername {

    @Autowired
    private PersistentTokenStore store;

    @Test
    @DisplayName("Extracts the username from a token")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testGetUsername() {
        final String subject;

        subject = store.getUsername(TokenConstants.TOKEN);

        Assertions.assertThat(subject)
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Extracts no username from an invalid token")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testGetUsername_InvalidToken() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> store.getUsername("abc");

        exception = Assertions.catchThrowableOfType(executable, InvalidTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Invalid token abc");
    }

}
