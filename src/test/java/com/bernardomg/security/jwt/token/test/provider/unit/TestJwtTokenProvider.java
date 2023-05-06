
package com.bernardomg.security.jwt.token.test.provider.unit;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.jwt.token.provider.JwtTokenProvider;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@DisplayName("JwtTokenProvider")
public class TestJwtTokenProvider {

    private final SecretKey key;

    private final String    keyValue = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

    private final JwtParser parser;

    public TestJwtTokenProvider() {
        super();

        key = Keys.hmacShaKeyFor(keyValue.getBytes(StandardCharsets.UTF_8));
        parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();
    }

    @Test
    @DisplayName("The token date is created around the token creation")
    public void testGenerateToken_Date() {
        final String   token;
        final Date     date;
        final Calendar calendarBefore;
        final Calendar calendarAfter;
        final Date     before;
        final Date     after;

        token = getProvider().generateToken("subject");
        date = parser.parseClaimsJws(token)
            .getBody()
            .getIssuedAt();

        calendarBefore = Calendar.getInstance();
        calendarBefore.setTime(new Date());
        calendarBefore.add(Calendar.MINUTE, -1);
        before = calendarBefore.getTime();

        calendarAfter = Calendar.getInstance();
        calendarAfter.setTime(new Date());
        calendarAfter.add(Calendar.MINUTE, 1);
        after = calendarAfter.getTime();

        Assertions.assertTrue(before.before(date));
        Assertions.assertTrue(after.after(date));
    }

    @Test
    @DisplayName("The token date matches the not before date")
    public void testGenerateToken_DateMatchNotBefore() {
        final String token;
        final Date   date;
        final Date   notBefore;

        token = getProvider().generateToken("subject");
        date = parser.parseClaimsJws(token)
            .getBody()
            .getIssuedAt();
        notBefore = parser.parseClaimsJws(token)
            .getBody()
            .getNotBefore();

        Assertions.assertEquals(date, notBefore);
    }

    @Test
    @DisplayName("The token expiration date is between the token creation and the limit")
    public void testGenerateToken_Expiration() {
        final String           token;
        final Date             date;
        final Date             start;
        final Date             limit;
        final JwtTokenProvider provider;

        provider = getProvider();

        provider.setValidity(10);

        token = provider.generateToken("subject");
        date = parser.parseClaimsJws(token)
            .getBody()
            .getExpiration();

        limit = new Date(System.currentTimeMillis() + (10 * 1000L));
        start = new Date();

        Assertions.assertTrue(start.before(date));
        Assertions.assertTrue(limit.after(date));
    }

    @Test
    @DisplayName("The token contains the received id")
    public void testGenerateToken_Id() {
        final String           token;
        final String           id;
        final String           parsedId;
        final JwtTokenProvider provider;

        id = "id";
        provider = getProvider();
        provider.setId(id);

        token = provider.generateToken("subject");
        parsedId = parser.parseClaimsJws(token)
            .getBody()
            .getId();

        Assertions.assertEquals(id, parsedId);
    }

    @Test
    @DisplayName("By default no id is included in the token")
    public void testGenerateToken_Id_Default() {
        final String token;
        final String id;

        token = getProvider().generateToken("subject");
        id = parser.parseClaimsJws(token)
            .getBody()
            .getId();

        Assertions.assertNull(id);
    }

    @Test
    @DisplayName("The token not before date is created around the token creation")
    public void testGenerateToken_NotBeforeDate() {
        final String   token;
        final Date     date;
        final Calendar calendarBefore;
        final Calendar calendarAfter;
        final Date     before;
        final Date     after;

        token = getProvider().generateToken("subject");
        date = parser.parseClaimsJws(token)
            .getBody()
            .getNotBefore();

        calendarBefore = Calendar.getInstance();
        calendarBefore.setTime(new Date());
        calendarBefore.add(Calendar.MINUTE, -1);
        before = calendarBefore.getTime();

        calendarAfter = Calendar.getInstance();
        calendarAfter.setTime(new Date());
        calendarAfter.add(Calendar.MINUTE, 1);
        after = calendarAfter.getTime();

        Assertions.assertTrue(before.before(date));
        Assertions.assertTrue(after.after(date));
    }

    @Test
    @DisplayName("The token contains the received subject")
    public void testGenerateToken_Subject() {
        final String token;
        final String subject;
        final String parsedSubject;

        subject = "subject";
        token = getProvider().generateToken(subject);
        parsedSubject = parser.parseClaimsJws(token)
            .getBody()
            .getSubject();

        Assertions.assertEquals(subject, parsedSubject);
    }

    private final JwtTokenProvider getProvider() {
        final JwtTokenProvider provider;

        provider = new JwtTokenProvider();
        provider.setKey(key);

        return provider;
    }

}
