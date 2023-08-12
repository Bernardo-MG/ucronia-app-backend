
package com.bernardomg.security.jwt.token.test.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.JwtSubjectTokenEncoder;
import com.bernardomg.security.token.TokenEncoder;
import com.bernardomg.security.token.test.constant.TokenConstants;

@DisplayName("JwtSubjectTokenEncoder - generate token")
class TestJwtSubjectTokenEncoderGenerateToken {

    private final TokenEncoder<String> encoder;

    public TestJwtSubjectTokenEncoderGenerateToken() {
        super();

        encoder = new JwtSubjectTokenEncoder(TokenConstants.KEY, 1);
    }

    @Test
    @DisplayName("Encodes a token")
    void testGenerateToken() {
        final String token;

        token = encoder.encode("subject");

        Assertions.assertThat(token)
            .isNotEmpty();
    }

}
