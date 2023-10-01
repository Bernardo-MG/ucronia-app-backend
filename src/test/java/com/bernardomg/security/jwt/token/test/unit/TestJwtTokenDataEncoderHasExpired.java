
package com.bernardomg.security.jwt.token.test.unit;

import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.ImmutableJwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenDataDecoder;
import com.bernardomg.security.jwt.token.JwtTokenDataEncoder;
import com.bernardomg.security.jwt.token.JwtTokenValidator;
import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenEncoder;
import com.bernardomg.security.token.test.constant.TokenConstants;

@DisplayName("JwtSubjectTokenEncoder - has expired")
class TestJwtTokenDataEncoderHasExpired {

    private final TokenEncoder      encoder;

    private final JwtTokenValidator validator;

    public TestJwtTokenDataEncoderHasExpired() {
        super();

        final TokenDecoder<JwtTokenData> decoder;

        encoder = new JwtTokenDataEncoder(TokenConstants.KEY);
        decoder = new JwtTokenDataDecoder(TokenConstants.KEY);
        validator = new JwtTokenValidator(decoder);
    }

    @Test
    @DisplayName("A new token is not expired")
    void testHasExpired_fromGeneratedToken() {
        final String       token;
        final Boolean      expired;
        final JwtTokenData data;

        data = ImmutableJwtTokenData.builder()
            .build();

        token = encoder.encode(data);
        expired = validator.hasExpired(token);

        Assertions.assertThat(expired)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is identified as such")
    void testHasExpired_fromGeneratedToken_expired() throws InterruptedException {
        final String       token;
        final Boolean      expired;
        final JwtTokenData data;

        data = ImmutableJwtTokenData.builder()
            .build();

        token = encoder.encode(data);

        TimeUnit.SECONDS.sleep(Double.valueOf(6)
            .longValue());

        expired = validator.hasExpired(token);

        Assertions.assertThat(expired)
            .isTrue();
    }

}
