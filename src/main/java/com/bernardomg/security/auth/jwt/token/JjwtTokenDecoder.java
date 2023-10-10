
package com.bernardomg.security.auth.jwt.token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import javax.crypto.SecretKey;

import com.bernardomg.security.auth.jwt.token.model.ImmutableJwtTokenData;
import com.bernardomg.security.auth.jwt.token.model.JwtTokenData;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * Token decoder based on the JJWT library.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class JjwtTokenDecoder implements TokenDecoder {

    /**
     * JWTS parser for reading tokens.
     */
    private final JwtParser parser;

    /**
     * Builds a decoder with the received key.
     *
     * @param secretKey
     *            secret key used for the token
     */
    public JjwtTokenDecoder(final SecretKey secretKey) {
        super();

        Objects.requireNonNull(secretKey);

        parser = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build();
    }

    @Override
    public final JwtTokenData decode(final String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        final Claims        claims;
        final LocalDateTime issuedAt;
        final LocalDateTime expiration;
        final LocalDateTime notBefore;

        // Acquire claims
        claims = parser.parseClaimsJws(token)
            .getBody();

        if (claims.getIssuedAt() != null) {
            issuedAt = claims.getIssuedAt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        } else {
            issuedAt = null;
        }
        if (claims.getExpiration() != null) {
            expiration = claims.getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        } else {
            expiration = null;
        }
        if (claims.getNotBefore() != null) {
            notBefore = claims.getNotBefore()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        } else {
            notBefore = null;
        }

        return ImmutableJwtTokenData.builder()
            .withId(claims.getId())
            .withSubject(claims.getSubject())
            .withAudience(claims.getAudience())
            .withIssuer(claims.getIssuer())
            .withIssuedAt(issuedAt)
            .withExpiration(expiration)
            .withNotBefore(notBefore)
            .build();
    }

}
