
package com.bernardomg.security.jwt.token.test.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.ImmutableJwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenDataEncoder;
import com.bernardomg.security.token.TokenEncoder;
import com.bernardomg.security.token.test.constant.TokenConstants;

@DisplayName("JwtSubjectTokenEncoder - generate token")
class TestJwtTokenDataEncoderGenerateToken {

    private final TokenEncoder encoder;

    public TestJwtTokenDataEncoderGenerateToken() {
        super();

        encoder = new JwtTokenDataEncoder(TokenConstants.KEY);
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
