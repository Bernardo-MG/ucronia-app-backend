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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.bernardomg.security.auth.entrypoint.ErrorResponseAuthenticationEntryPoint;
import com.bernardomg.security.auth.permission.AuthorizedResourceAspect;
import com.bernardomg.security.auth.permission.AuthorizedResourceValidator;
import com.bernardomg.security.auth.permission.SpringAuthorizedResourceValidator;

/**
 * Authentication configuration.
 *
 * @author Bernardo Martínez Garrido
 *
 */
@Configuration
public class AuthConfig {

    /**
     * Default constructor.
     */
    public AuthConfig() {
        super();
    }

    @Bean("authenticationEntryPoint")
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new ErrorResponseAuthenticationEntryPoint();
    }

    @Bean("authorizedResourceAspect")
    @ConditionalOnProperty(prefix = "security.resource", name = "enabled", havingValue = "true", matchIfMissing = true)
    public AuthorizedResourceAspect getAuthorizedResourceAspect() {
        final AuthorizedResourceValidator validator;

        validator = new SpringAuthorizedResourceValidator();
        return new AuthorizedResourceAspect(validator);
    }

}
