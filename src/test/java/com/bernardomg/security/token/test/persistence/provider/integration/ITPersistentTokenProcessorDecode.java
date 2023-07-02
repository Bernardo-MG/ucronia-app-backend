
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.provider.PersistentTokenProcessor;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - has expired")
class ITPersistentTokenProcessorDecode {

    private final PersistentTokenProcessor validator;

    @Autowired
    public ITPersistentTokenProcessorDecode(final TokenRepository tokenRepository, final TokenService tokenService) {
        super();

        validator = new PersistentTokenProcessor(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("Decodes a token")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testDecode() {
        final String token;
        final String subject;

        token = TokenConstants.TOKEN;

        subject = validator.decode(token)
            .get()
            .getExtendedInformation();

        Assertions.assertThat(subject)
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Decodes an expired token")
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testDecode_Expired() {
        final String token;
        final String subject;

        token = TokenConstants.TOKEN;

        subject = validator.decode(token)
            .get()
            .getExtendedInformation();

        Assertions.assertThat(subject)
            .isEqualTo("admin");
    }

}
