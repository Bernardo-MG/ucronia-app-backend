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

package com.bernardomg.security.email.sender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Email sender for security operations which integrates with Spring Mail.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class SpringMailSecurityEmailSender implements SecurityMessageSender {

    private final String         fromEmail;

    private final JavaMailSender mailSender;

    private final String         passwordRecoverySubject = "Password recovery";

    private final String         passwordRecoveryText    = "Visit %s to reset password";

    private final String         passwordRecoveryUrl;

    private final String         signUpSubject           = "";

    private final String         signUpText              = "";

    public SpringMailSecurityEmailSender(@NonNull final String from, @NonNull final String passRecoveryUrl,
            @NonNull final JavaMailSender mSender) {
        super();

        fromEmail = from;
        mailSender = mSender;
        passwordRecoveryUrl = passRecoveryUrl;
    }

    @Override
    public final void sendPasswordRecoveryMessage(final String email, final String token) {
        final SimpleMailMessage message;
        final String            recoveryUrl;
        final String            passwordRecoveryEmailText;

        log.debug("Sending password recovery email to {}", email);

        if (passwordRecoveryUrl.endsWith("/")) {
            recoveryUrl = String.format("%s%s", passwordRecoveryUrl, token);
        } else {
            recoveryUrl = String.format("%s/%s", passwordRecoveryUrl, token);
        }
        passwordRecoveryEmailText = String.format(passwordRecoveryText, recoveryUrl);

        message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject(passwordRecoverySubject);
        message.setText(passwordRecoveryEmailText);
        mailSender.send(message);

        log.debug("Sent password recovery email to {}", email);
    }

    @Override
    public final void sendSignUpMessage(final String username, final String email) {
        final SimpleMailMessage message;

        message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject(signUpSubject);
        message.setText(signUpText);
        mailSender.send(message);
    }

    @Override
    public final void sendUserRegisteredMessage(final String email, final String token) {
        // TODO Auto-generated method stub
        log.debug("Sending user registered email to {}", email);

        log.debug("Sent user registered email to {}", email);
    }

}
