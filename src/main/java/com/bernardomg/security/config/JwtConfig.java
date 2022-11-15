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

import java.nio.charset.Charset;
import java.security.Key;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.bernardomg.security.jwt.filter.JwtTokenFilter;
import com.bernardomg.security.jwt.property.JwtProperties;
import com.bernardomg.security.jwt.token.JwtTokenProvider;
import com.bernardomg.security.jwt.token.JwtTokenValidator;
import com.bernardomg.security.token.provider.TokenProvider;
import com.bernardomg.security.token.provider.TokenValidator;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * Authentication configuration.
 *
 * @author Bernardo Martínez Garrido
 *
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class JwtConfig {

    public JwtConfig() {
        super();
    }

    @Bean("jwtSecretKey")
    @ConditionalOnProperty(value = "jwt.secret", matchIfMissing = false)
    public Key getJwtSecretKey(final JwtProperties properties) {
        return Keys.hmacShaKeyFor(properties.getSecret()
            .getBytes(Charset.forName("UTF-8")));
    }

    @Bean("jwtTokenFilter")
    public JwtTokenFilter getJwtTokenFilter(final UserDetailsService userDetService, final TokenValidator processor) {
        return new JwtTokenFilter(userDetService, processor);
    }

    /**
     * Default token validator. With default seeds.
     *
     * @return default token validator
     */
    @Bean("tokenValidator")
    @ConditionalOnMissingBean(name = "jwtSecretKey")
    public TokenValidator getJwtTokenValidator() {
        log.info("Using default token validator");
        return new JwtTokenValidator();
    }

    /**
     * Token validator with security seed.
     *
     * @param secret
     *            secret for the seed
     * @return secure token validator
     */
    @Bean("tokenValidator")
    @ConditionalOnBean(name = "jwtSecretKey")
    public TokenValidator getJwtTokenValidatorWithSecret(@Qualifier("jwtSecretKey") final Key secret) {
        log.info("Using secured token validator");
        return new JwtTokenValidator(secret);
    }

    /**
     * Default token provider. With default seeds.
     *
     * @return default token provider
     */
    @Bean("tokenProvider")
    @ConditionalOnMissingBean(name = "jwtSecretKey")
    public TokenProvider getTokenProvider() {
        log.info("Using default token provider");
        return new JwtTokenProvider();
    }

    /**
     * Token provider with security seed.
     *
     * @param secret
     *            secret for the seed
     * @param properties
     *            JWT security properties
     * @return secure token provider
     */
    @Bean("tokenProvider")
    @ConditionalOnBean(name = "jwtSecretKey")
    public TokenProvider getTokenProviderWithSecret(@Qualifier("jwtSecretKey") final Key secret,
            final JwtProperties properties) {
        log.info("Using secured token provider with {} seconds of validity", properties.getValidity());
        return new JwtTokenProvider(secret, properties.getValidity());
    }

}
