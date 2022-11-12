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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.login.service.LoginService;
import com.bernardomg.security.login.service.springframework.SpringSecurityTokenLoginService;
import com.bernardomg.security.password.service.DefaultPasswordResetService;
import com.bernardomg.security.password.service.PasswordResetService;
import com.bernardomg.security.password.validation.ChangePasswordPassValidator;
import com.bernardomg.security.password.validation.ChangePasswordValidator;
import com.bernardomg.security.signup.service.MailSignUpService;
import com.bernardomg.security.signup.service.SignUpService;
import com.bernardomg.security.token.TokenProvider;

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

    @Bean("resetPasswordService")
    public PasswordResetService getResetPasswordService(final UserRepository repository,
            final PasswordEncoder passwordEncoder, final ChangePasswordValidator validator,
            final ChangePasswordPassValidator passValidator) {
        return new DefaultPasswordResetService(repository, passwordEncoder, validator, passValidator);
    }

    @Bean("userRegistrationService")
    public SignUpService getUserRegistrationService(final UserRepository repository,
            final SecurityEmailSender mailSender) {
        return new MailSignUpService(repository, mailSender);
    }

}
