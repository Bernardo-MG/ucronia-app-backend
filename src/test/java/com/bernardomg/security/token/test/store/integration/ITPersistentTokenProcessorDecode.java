
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - decode")
class ITPersistentTokenProcessorDecode {

    private final PersistentTokenStore store;

    @Autowired
    public ITPersistentTokenProcessorDecode(final TokenRepository tokenRepository, final TokenService tokenService) {
        super();

        store = new PersistentTokenStore(tokenRepository, tokenService, 1000);
    }

    @Test
    @DisplayName("Decodes a token")
    void testDecode() {
        final String token;
        final String subject;

        token = TokenConstants.TOKEN;

        subject = store.getUsername(token);

        Assertions.assertThat(subject)
            .isEqualTo("admin");
    }

}
