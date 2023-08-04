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

package com.bernardomg.security.email.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.bernardomg.security.email.config.property.SecurityEmailProperties;
import com.bernardomg.security.email.sender.DisabledSecurityMessageSender;
import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.email.sender.SpringMailSecurityEmailSender;

import lombok.extern.slf4j.Slf4j;

/**
 * Security configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityEmailProperties.class)
@Slf4j
public class SecurityEmailConfig {

    public SecurityEmailConfig() {
        super();
    }

    @Bean("securityEmailSender")
    @ConditionalOnProperty(prefix = "spring.mail", name = "host", havingValue = "true", matchIfMissing = true)
    public SecurityMessageSender getDefaultSecurityEmailSender() {
        log.debug("Disabled security messages");
        return new DisabledSecurityMessageSender();
    }

    @Bean("securityEmailSender")
    @ConditionalOnProperty(prefix = "spring.mail", name = "host")
    public SecurityMessageSender getSecurityEmailSender(final SecurityEmailProperties properties,
            final MailProperties mailProperties, final JavaMailSender mailSender) {
        log.debug("Using email for security messages");
        return new SpringMailSecurityEmailSender(mailProperties.getUsername(), properties.getPasswordRecoveryUrl(),
            mailSender);
    }

}
