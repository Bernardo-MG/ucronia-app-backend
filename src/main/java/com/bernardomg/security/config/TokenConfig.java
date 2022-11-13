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

package com.bernardomg.security.config;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.security.jwt.property.JwtProperties;
import com.bernardomg.security.jwt.token.JwtTokenProvider;
import com.bernardomg.security.jwt.token.JwtTokenValidator;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.provider.TokenProvider;
import com.bernardomg.security.token.service.PersistentTokenService;
import com.bernardomg.security.token.service.TokenService;

/**
 * Security configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class TokenConfig {

    public TokenConfig() {
        super();
    }

    @Bean("tokenProcessor")
    public JwtTokenValidator getTokenProcessor(final SecretKey key) {
        return new JwtTokenValidator(key);
    }

    @Bean("tokenProvider")
    public TokenProvider getTokenProvider(final SecretKey key, final JwtProperties properties) {
        return new JwtTokenProvider(key, properties.getValidity());
    }

    @Bean("tokenService")
    public TokenService getTokenService(final TokenRepository tokenRepository) {
        return new PersistentTokenService(tokenRepository);
    }

}
