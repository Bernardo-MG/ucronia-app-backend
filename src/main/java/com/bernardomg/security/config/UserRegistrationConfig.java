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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.service.DefaultResetPasswordService;
import com.bernardomg.security.password.service.ResetPasswordService;
import com.bernardomg.security.password.validation.ChangePasswordPassValidator;
import com.bernardomg.security.password.validation.ChangePasswordValidator;
import com.bernardomg.security.registration.service.DefaultUserRegistrationService;
import com.bernardomg.security.registration.service.UserRegistrationService;

/**
 * Security configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class UserRegistrationConfig {

    public UserRegistrationConfig() {
        super();
    }

    @Bean("resetPasswordService")
    public ResetPasswordService getResetPasswordService(final UserRepository repository,
            final PasswordEncoder passwordEncoder, final ChangePasswordValidator validator,
            final ChangePasswordPassValidator passValidator) {
        return new DefaultResetPasswordService(repository, passwordEncoder, validator, passValidator);
    }

    @Bean("userRegistrationService")
    public UserRegistrationService getUserRegistrationService(final UserRepository repository) {
        return new DefaultUserRegistrationService(repository);
    }

}
