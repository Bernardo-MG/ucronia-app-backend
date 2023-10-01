
package com.bernardomg.security.jwt.token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import javax.crypto.SecretKey;

import com.bernardomg.security.token.TokenDecoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * JWT data token decoder. Builds a {@code JwtTokenData} from a JWT token.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class JwtTokenDataDecoder implements TokenDecoder<JwtTokenData> {

    /**
     * JWT parser for reading tokens.
     */
    private final JwtParser parser;

    /**
     * Builds a decoder with the received parser.
     *
     * @param prsr
     *            JWT parser
     */
    public JwtTokenDataDecoder(final JwtParser prsr) {
        super();

        parser = Objects.requireNonNull(prsr);
    }

    /**
     * Builds a decoder with the received key.
     *
     * @param key
     *            secret key for the token
     */
    public JwtTokenDataDecoder(final SecretKey key) {
        super();

        Objects.requireNonNull(key);

        parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();
    }

    @Override
    public final JwtTokenData decode(final String token) {
        final Claims        claims;
        final LocalDateTime issuedAt;
        final LocalDateTime expiration;
        final LocalDateTime notBefore;

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
