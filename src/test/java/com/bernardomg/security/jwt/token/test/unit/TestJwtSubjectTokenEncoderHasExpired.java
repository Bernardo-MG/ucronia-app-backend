
package com.bernardomg.security.jwt.token.test.unit;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.JwtSubjectTokenEncoder;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenDataDecoder;
import com.bernardomg.security.jwt.token.JwtTokenValidator;
import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenEncoder;

import io.jsonwebtoken.security.Keys;

@DisplayName("JwtSubjectTokenEncoder - has expired")
public class TestJwtSubjectTokenEncoderHasExpired {

    private final TokenEncoder<String> encoder;

    private final JwtTokenValidator    validator;

    public TestJwtSubjectTokenEncoderHasExpired() {
        super();

        final SecretKey                  key;
        final TokenDecoder<JwtTokenData> decoder;

        key = Keys.hmacShaKeyFor(
            "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
                .getBytes(Charset.forName("UTF-8")));

        encoder = new JwtSubjectTokenEncoder(key, 5);
        decoder = new JwtTokenDataDecoder(key);
        validator = new JwtTokenValidator(decoder);
    }

    @Test
    @DisplayName("A new token is not expired")
    public void testHasExpired_fromGeneratedToken() {
        final String  token;
        final Boolean expired;

        token = encoder.encode("subject");
        expired = validator.hasExpired(token);

        Assertions.assertFalse(expired);
    }

    @Test
    @DisplayName("An expired token is identified as such")
    public void testHasExpired_fromGeneratedToken_expired() throws InterruptedException {
        final String  token;
        final Boolean expired;

        token = encoder.encode("subject");

        TimeUnit.SECONDS.sleep(Double.valueOf(6)
            .longValue());

        expired = validator.hasExpired(token);

        Assertions.assertTrue(expired);
    }

}
