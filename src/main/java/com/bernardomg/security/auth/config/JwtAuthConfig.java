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

package com.bernardomg.security.auth.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.security.auth.jwt.token.DefaultTokenEncoder;
import com.bernardomg.security.auth.jwt.token.JwtTokenData;
import com.bernardomg.security.auth.jwt.token.JwtTokenDataDecoder;
import com.bernardomg.security.auth.jwt.token.JwtTokenValidator;
import com.bernardomg.security.auth.jwt.token.TokenDecoder;
import com.bernardomg.security.auth.jwt.token.TokenEncoder;
import com.bernardomg.security.auth.jwt.token.TokenValidator;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT authentication configuration.
 *
 * @author Bernardo Martínez Garrido
 *
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class JwtAuthConfig {

    /**
     * Default constructor.
     */
    public JwtAuthConfig() {
        super();
    }

    /**
     * Returns the token decoder.
     *
     * @param properties
     *            JWT configuration properties
     * @return the token encoder
     */
    @Bean("jwtTokenDecoder")
    public TokenDecoder<JwtTokenData> getTokenDecoder(final JwtProperties properties) {
        final SecretKey key;

        key = Keys.hmacShaKeyFor(properties.getSecret()
            .getBytes(StandardCharsets.UTF_8));
        return new JwtTokenDataDecoder(key);
    }

    /**
     * Returns the token encoder.
     *
     * @param properties
     *            JWT configuration properties
     * @return the token encoder
     */
    @Bean("jwtTokenEncoder")
    public TokenEncoder getTokenEncoder(final JwtProperties properties) {
        final SecretKey key;

        key = Keys.hmacShaKeyFor(properties.getSecret()
            .getBytes(StandardCharsets.UTF_8));

        log.info("Security tokens will have a validity of {}", properties.getValidity());

        return new DefaultTokenEncoder(key);
    }

    /**
     * Returns the token validator.
     *
     * @param decoder
     *            token decoder
     * @return the token validator
     */
    @Bean("jwtTokenValidator")
    public TokenValidator getTokenValidator(final TokenDecoder<JwtTokenData> decoder) {
        return new JwtTokenValidator(decoder);
    }

}
