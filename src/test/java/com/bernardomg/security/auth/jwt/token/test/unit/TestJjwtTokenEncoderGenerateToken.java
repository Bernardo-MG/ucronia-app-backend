
package com.bernardomg.security.auth.jwt.token.test.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.auth.jwt.token.JjwtTokenEncoder;
import com.bernardomg.security.auth.jwt.token.TokenEncoder;
import com.bernardomg.security.auth.jwt.token.model.ImmutableJwtTokenData;
import com.bernardomg.security.auth.jwt.token.model.JwtTokenData;
import com.bernardomg.security.user.token.test.config.constant.TokenConstants;

@DisplayName("JjwtTokenEncoder - generate token")
class TestJjwtTokenEncoderGenerateToken {

    private final TokenEncoder encoder;

    public TestJjwtTokenEncoderGenerateToken() {
        super();

        encoder = new JjwtTokenEncoder(TokenConstants.KEY);
    }

    @Test
    @DisplayName("Encodes a token")
    void testGenerateToken() {
        final String       token;
        final JwtTokenData data;

        data = ImmutableJwtTokenData.builder()
            .build();

        token = encoder.encode(data);

        Assertions.assertThat(token)
            .isNotEmpty();
    }

}
