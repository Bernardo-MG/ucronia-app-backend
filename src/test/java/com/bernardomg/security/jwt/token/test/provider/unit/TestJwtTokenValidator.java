
package com.bernardomg.security.jwt.token.test.provider.unit;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.provider.JwtTokenValidator;
import com.bernardomg.security.token.provider.TokenValidator;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@DisplayName("JwtTokenValidator")
public class TestJwtTokenValidator {

    private final SecretKey      key;

    private final String         keyValue = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

    private final String         subject  = "subject";

    private final TokenValidator tokenValidator;

    public TestJwtTokenValidator() {
        super();

        key = Keys.hmacShaKeyFor(keyValue.getBytes(StandardCharsets.UTF_8));
        tokenValidator = new JwtTokenValidator(key);
    }

    @Test
    @DisplayName("Can acquire the subject from the token")
    public void testGetSubject() {
        final String readSubject;
        final String token;

        token = getJwtBuilder().setSubject(subject)
            .compact();

        readSubject = tokenValidator.getSubject(token);

        Assertions.assertEquals(subject, readSubject);
    }

    @Test
    @DisplayName("An expired token is identified as such")
    public void testHasExpired_Expired() {
        final String   token;
        final Calendar calendarExpiration;
        final Date     expiration;
        final Boolean  expired;

        calendarExpiration = Calendar.getInstance();
        calendarExpiration.setTime(new Date());
        calendarExpiration.add(Calendar.MINUTE, -1);
        expiration = calendarExpiration.getTime();

        token = getJwtBuilder().setExpiration(expiration)
            .compact();

        expired = tokenValidator.hasExpired(token);

        Assertions.assertTrue(expired);
    }

    @Test
    @DisplayName("A not expired token is identified as such")
    public void testHasExpired_NotExpired() {
        final String   token;
        final Calendar calendarExpiration;
        final Date     expiration;
        final Boolean  expired;

        calendarExpiration = Calendar.getInstance();
        calendarExpiration.setTime(new Date());
        calendarExpiration.add(Calendar.MINUTE, 1);
        expiration = calendarExpiration.getTime();

        token = getJwtBuilder().setExpiration(expiration)
            .compact();

        expired = tokenValidator.hasExpired(token);

        Assertions.assertFalse(expired);
    }

    private final JwtBuilder getJwtBuilder() {
        return Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS512)
            .setSubject(subject);
    }

}
