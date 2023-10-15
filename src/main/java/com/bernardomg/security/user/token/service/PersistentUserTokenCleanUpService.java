/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.security.user.token.service;

import java.util.Collection;
import java.util.Objects;

import com.bernardomg.security.user.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Cleans up tokens through {@link PersistentUserToken}.
 * <p>
 * Removes tokens which match any of these cases:
 * <p>
 * <ul>
 * <li>Consumed</li>
 * <li>Revoked</li>
 * <li>Expired</li>
 * </ul>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class PersistentUserTokenCleanUpService implements TokenCleanUpService {

    /**
     * User token repository.
     */
    private final UserTokenRepository userTokenRepository;

    public PersistentUserTokenCleanUpService(final UserTokenRepository respository) {
        super();

        userTokenRepository = Objects.requireNonNull(respository);
    }

    @Override
    public final void cleanUpTokens() {
        final Collection<PersistentUserToken> tokens;

        // Expiration date before now
        // Revoked
        // Consumed
        tokens = userTokenRepository.findAllFinished();

        log.info("Removing {} finished tokens", tokens.size());

        userTokenRepository.deleteAll(tokens);
    }

}
