
package com.bernardomg.security.jwt.token.test.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.JwtSubjectTokenEncoder;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.TokenEncoder;

@DisplayName("JwtSubjectTokenEncoder - generate token")
public class TestJwtSubjectTokenEncoderGenerateToken {

    private final TokenEncoder<String> encoder;

    public TestJwtSubjectTokenEncoderGenerateToken() {
        super();

        encoder = new JwtSubjectTokenEncoder(TokenConstants.KEY, 1);
    }

    @Test
    @DisplayName("Encodes a token")
    public void testGenerateToken() {
        final String token;

        token = encoder.encode("subject");

        Assertions.assertFalse(token.isEmpty());
    }

}
