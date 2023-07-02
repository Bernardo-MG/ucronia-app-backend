
package com.bernardomg.security.jwt.token.test.unit;

import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.JwtSubjectTokenEncoder;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenDataDecoder;
import com.bernardomg.security.jwt.token.JwtTokenValidator;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenEncoder;

@DisplayName("JwtSubjectTokenEncoder - has expired")
class TestJwtSubjectTokenEncoderHasExpired {

    private final TokenEncoder<String> encoder;

    private final JwtTokenValidator    validator;

    public TestJwtSubjectTokenEncoderHasExpired() {
        super();

        final TokenDecoder<JwtTokenData> decoder;

        encoder = new JwtSubjectTokenEncoder(TokenConstants.KEY, 5);
        decoder = new JwtTokenDataDecoder(TokenConstants.KEY);
        validator = new JwtTokenValidator(decoder);
    }

    @Test
    @DisplayName("A new token is not expired")
    void testHasExpired_fromGeneratedToken() {
        final String  token;
        final Boolean expired;

        token = encoder.encode("subject");
        expired = validator.hasExpired(token);

        Assertions.assertThat(expired)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is identified as such")
    void testHasExpired_fromGeneratedToken_expired() throws InterruptedException {
        final String  token;
        final Boolean expired;

        token = encoder.encode("subject");

        TimeUnit.SECONDS.sleep(Double.valueOf(6)
            .longValue());

        expired = validator.hasExpired(token);

        Assertions.assertThat(expired)
            .isTrue();
    }

}
