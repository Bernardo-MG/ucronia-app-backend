
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.provider.PersistentTokenStore;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - decode")
class ITPersistentTokenProcessorDecode {

    private final PersistentTokenStore validator;

    @Autowired
    public ITPersistentTokenProcessorDecode(final TokenRepository tokenRepository, final TokenService tokenService) {
        super();

        validator = new PersistentTokenStore(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("Decodes a token")
    void testDecode() {
        final String token;
        final String subject;

        token = TokenConstants.TOKEN;

        subject = validator.decode(token)
            .getExtendedInformation();

        Assertions.assertThat(subject)
            .isEqualTo("admin");
    }

}
