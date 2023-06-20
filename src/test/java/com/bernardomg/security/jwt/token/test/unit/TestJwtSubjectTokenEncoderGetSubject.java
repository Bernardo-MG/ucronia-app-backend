
package com.bernardomg.security.jwt.token.test.unit;

import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.JwtSubjectTokenEncoder;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenDataDecoder;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenEncoder;

import io.jsonwebtoken.ExpiredJwtException;

@DisplayName("JwtSubjectTokenEncoder - get subject")
public class TestJwtSubjectTokenEncoderGetSubject {

    private final TokenDecoder<JwtTokenData> decoder;

    private final TokenEncoder<String>       encoder;

    public TestJwtSubjectTokenEncoderGetSubject() {
        super();

        encoder = new JwtSubjectTokenEncoder(TokenConstants.KEY, 1);
        decoder = new JwtTokenDataDecoder(TokenConstants.KEY);
    }

    @Test
    @DisplayName("Recovers the subject from a token")
    public void testGetSubject_fromGeneratedToken() {
        final String token;
        final String subject;

        token = encoder.encode("subject");
        subject = decoder.decode(token)
            .getSubject();

        Assertions.assertThat(subject)
            .isEqualTo("subject");
    }

    @Test
    @DisplayName("Recovering the subject from an expired token generates an exception")
    public void testGetSubject_fromGeneratedToken_expired() throws InterruptedException {
        final String           token;
        final ThrowingCallable executable;

        token = encoder.encode("subject");

        TimeUnit.SECONDS.sleep(Double.valueOf(2)
            .longValue());

        executable = () -> decoder.decode(token);

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(ExpiredJwtException.class);
    }

}
