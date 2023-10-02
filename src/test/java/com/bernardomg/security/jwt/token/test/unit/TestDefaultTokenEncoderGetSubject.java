
package com.bernardomg.security.jwt.token.test.unit;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.DefaultTokenEncoder;
import com.bernardomg.security.jwt.token.ImmutableJwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenDataDecoder;
import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenEncoder;
import com.bernardomg.security.token.test.constant.TokenConstants;

import io.jsonwebtoken.ExpiredJwtException;

@DisplayName("DefaultTokenEncoder - get subject")
class TestDefaultTokenEncoderGetSubject {

    private final TokenDecoder<JwtTokenData> decoder;

    private final TokenEncoder               encoder;

    public TestDefaultTokenEncoderGetSubject() {
        super();

        encoder = new DefaultTokenEncoder(TokenConstants.KEY);
        decoder = new JwtTokenDataDecoder(TokenConstants.KEY);
    }

    @Test
    @DisplayName("Recovers the subject from a token")
    void testGetSubject_fromGeneratedToken() {
        final String       token;
        final String       subject;
        final JwtTokenData data;

        data = ImmutableJwtTokenData.builder()
            .withSubject("subject")
            .build();

        token = encoder.encode(data);
        subject = decoder.decode(token)
            .getSubject();

        Assertions.assertThat(subject)
            .isEqualTo("subject");
    }

    @Test
    @DisplayName("Recovering the subject from an expired token generates an exception")
    void testGetSubject_fromGeneratedToken_expired() throws InterruptedException {
        final String           token;
        final ThrowingCallable executable;
        final JwtTokenData     data;

        data = ImmutableJwtTokenData.builder()
            .withSubject("subject")
            .withExpiration(LocalDateTime.now()
                .plusSeconds(-1))
            .build();

        token = encoder.encode(data);

        // TODO: This is a test for the decoder, test it
        executable = () -> decoder.decode(token);

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(ExpiredJwtException.class);
    }

}
