/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.security.jwt.token;

import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import com.bernardomg.security.token.provider.TokenProvider;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT token provider.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class JwtTokenProvider implements TokenProvider {

    /**
     * Secret key for generating tokens. Created from the secret received when constructing the provider.
     */
    private final Optional<Key>     key;

    /**
     * Token validity time in seconds.
     */
    private final Optional<Integer> validity;

    /**
     * Default constructor for the provider. Makes use of the default security seed.
     */
    public JwtTokenProvider() {
        super();

        key = Optional.empty();
        validity = Optional.empty();
    }

    /**
     * Constructs a provider with the received arguments.
     *
     * @param secretKey
     *            key used when generating tokens
     * @param validityTime
     *            token validity time in seconds
     */
    public JwtTokenProvider(final Key secretKey, final Integer validityTime) {
        super();

        Objects.requireNonNull(secretKey);
        key = Optional.of(secretKey);

        Objects.requireNonNull(validityTime);
        validity = Optional.of(validityTime);
    }

    @Override
    public final void closeToken(final String token) {}

    @Override
    public final String generateToken(final String subject) {
        final Date       expiration;
        final Date       issuedAt;
        final String     token;
        final JwtBuilder builder;

        // Issued right now
        issuedAt = new Date();

        log.debug("Issue date for subject {}: {}", subject, issuedAt);

        builder = Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(issuedAt);

        if (validity.isPresent()) {
            // Expires in a number of seconds equal to validity
            expiration = new Date(System.currentTimeMillis() + (validity.get() * 1000L));
            builder.setExpiration(expiration);
            log.debug("Expiration date for subject {}: {}", subject, expiration);
        }

        if (key.isPresent()) {
            builder.signWith(key.get(), SignatureAlgorithm.HS512);
            log.debug("Signed token for subject {}", subject);
        }

        token = builder.compact();

        log.debug("Created token for subject {}", subject);

        return token;
    }

}
