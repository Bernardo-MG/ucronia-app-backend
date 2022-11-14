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

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.login.service.LoginService;
import com.bernardomg.security.login.service.springframework.SpringSecurityTokenLoginService;
import com.bernardomg.security.password.recovery.service.DefaultPasswordRecoveryService;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.signup.service.MailSignUpService;
import com.bernardomg.security.signup.service.SignUpService;
import com.bernardomg.security.token.persistence.provider.PersistentTokenProcessor;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.provider.TokenProcessor;
import com.bernardomg.security.token.provider.TokenProvider;

/**
 * Security configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class SecurityServiceConfig {

    public SecurityServiceConfig() {
        super();
    }

    @Bean("loginService")
    public LoginService getLoginService(final UserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder, final TokenProvider tokenProv) {
        return new SpringSecurityTokenLoginService(userDetailsService, passwordEncoder, tokenProv);
    }

    @Bean("passwordRecoveryService")
    public PasswordRecoveryService getPasswordRecoveryService(final UserRepository repository,
            final UserDetailsService userDetailsService, final SecurityEmailSender mailSender,
            final PasswordEncoder passwordEncoder, final TokenRepository tokenRepository,
            final TokenService tokenService) {
        final TokenProcessor tokenProcessor;

        tokenProcessor = new PersistentTokenProcessor(tokenRepository, tokenService);

        return new DefaultPasswordRecoveryService(repository, userDetailsService, mailSender, tokenProcessor,
            passwordEncoder);
    }

    @Bean("springTokenService")
    public TokenService getTokenService() {
        final KeyBasedPersistenceTokenService tokenService;

        tokenService = new KeyBasedPersistenceTokenService();
        // TODO: add to config
        tokenService.setServerInteger(123);
        tokenService.setServerSecret("abc");
        tokenService.setSecureRandom(new SecureRandom());

        return tokenService;
    }

    @Bean("userRegistrationService")
    public SignUpService getUserRegistrationService(final UserRepository repository,
            final SecurityEmailSender mailSender) {
        return new MailSignUpService(repository, mailSender);
    }

}
