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

package com.bernardomg.security.jwt.token;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.crypto.SecretKey;

import com.bernardomg.security.token.TokenEncoder;

import lombok.extern.slf4j.Slf4j;

/**
 * Encodes a token from the subject name.
 * <h2>Claims</h2>
 * <table>
 * <caption>Claims values</caption>
 * <tr>
 * <th>Claim</th>
 * <th>Value</th>
 * </tr>
 * <tr>
 * <td>Subject</td>
 * <td>Received when encoding</td>
 * </tr>
 * <tr>
 * <td>Issued at</td>
 * <td>Current date</td>
 * </tr>
 * <tr>
 * <td>Validity time</td>
 * <td>Current date</td>
 * </tr>
 * <tr>
 * <td>Expiration</td>
 * <td>Current date + validity time</td>
 * </tr>
 * </table>
 * <p>
 * The validity time is received in the constructor, and measures the number of seconds a token will stay valid.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class JwtSubjectTokenEncoder implements TokenEncoder<String> {

    /**
     * Wrapped encoder based on a token data structure.
     */
    private final JwtTokenDataEncoder tokenDataEncoder;

    /**
     * Token validity time in seconds.
     */
    private final Duration            validity;

    /**
     * Constructs a provider with the received arguments.
     *
     * @param secretKey
     *            key used when generating tokens
     * @param duration
     *            token validity duration
     */
    public JwtSubjectTokenEncoder(final SecretKey secretKey, final Duration duration) {
        super();

        tokenDataEncoder = new JwtTokenDataEncoder(secretKey);
        validity = Objects.requireNonNull(duration);
    }

    @Override
    public final String encode(final String subject) {
        final LocalDateTime expiration;
        final LocalDateTime issuedAt;
        final String        token;
        final JwtTokenData  data;

        // Issued right now
        issuedAt = LocalDateTime.now();
        // Expires in a number of seconds equal to validity
        expiration = LocalDateTime.now()
            .plus(validity);

        // Build token data for the wrapped encoder
        data = ImmutableJwtTokenData.builder()
            .withSubject(subject)
            .withIssuedAt(issuedAt)
            .withNotBefore(issuedAt)
            .withExpiration(expiration)
            .build();

        token = tokenDataEncoder.encode(data);

        log.debug("Created token for subject {} with expiration date {}", subject, expiration);

        return token;
    }

}
