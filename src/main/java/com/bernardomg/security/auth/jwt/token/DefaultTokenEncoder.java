/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.security.auth.jwt.token;

import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import com.bernardomg.security.token.TokenEncoder;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT data token encoder.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultTokenEncoder implements TokenEncoder {

    /**
     * Secret key for generating tokens. Created from the secret received when constructing the provider.
     */
    private final SecretKey key;

    /**
     * Constructs an encoder with the received arguments.
     *
     * @param secretKey
     *            key used when generating tokens
     */
    public DefaultTokenEncoder(final SecretKey secretKey) {
        super();

        key = Objects.requireNonNull(secretKey);
    }

    @Override
    public final String encode(final JwtTokenData data) {
        final String     token;
        final Date       issuedAt;
        final Date       expiration;
        final Date       notBefore;
        final JwtBuilder jwtBuilder;

        jwtBuilder = Jwts.builder()
            .setId(data.getId())
            .setIssuer(data.getIssuer())
            .setSubject(data.getSubject())
            .setAudience(data.getAudience())
            .claim("permissions", data.getPermissions());

        // TODO: Use optional
        if (data.getIssuedAt() != null) {
            issuedAt = java.util.Date.from(data.getIssuedAt()
                .atZone(ZoneId.systemDefault())
                .toInstant());
            jwtBuilder.setIssuedAt(issuedAt);
        }
        if (data.getExpiration() != null) {
            expiration = java.util.Date.from(data.getExpiration()
                .atZone(ZoneId.systemDefault())
                .toInstant());
            jwtBuilder.setExpiration(expiration);
        }
        if (data.getNotBefore() != null) {
            notBefore = java.util.Date.from(data.getNotBefore()
                .atZone(ZoneId.systemDefault())
                .toInstant());
            jwtBuilder.setNotBefore(notBefore);
        }

        token = jwtBuilder.signWith(key, SignatureAlgorithm.HS512)
            .compact();

        log.debug("Created token from {}", data);

        return token;
    }

}
