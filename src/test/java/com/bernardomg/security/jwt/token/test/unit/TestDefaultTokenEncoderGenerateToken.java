
package com.bernardomg.security.jwt.token.test.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.DefaultTokenEncoder;
import com.bernardomg.security.jwt.token.ImmutableJwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.token.TokenEncoder;
import com.bernardomg.security.token.test.constant.TokenConstants;

@DisplayName("DefaultTokenEncoder - generate token")
class TestDefaultTokenEncoderGenerateToken {

    private final TokenEncoder encoder;

    public TestDefaultTokenEncoderGenerateToken() {
        super();

        encoder = new DefaultTokenEncoder(TokenConstants.KEY);
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
