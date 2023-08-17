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

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

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

    private final String               fromEmail;

    private final JavaMailSender       mailSender;

    private final String               passwordRecoverySubject = "Password recovery";

    private final String               passwordRecoveryUrl;

    private final SpringTemplateEngine templateEngine;

    private final String               userRegisteredSubject   = "User registered";

    private final String               userRegisteredUrl;

    public SpringMailSecurityEmailSender(@NonNull final SpringTemplateEngine templateEng, @NonNull final String from,
            @NonNull final String passRecoveryUrl, @NonNull final String userRegUrl,
            @NonNull final JavaMailSender mSender) {
        super();

        // TODO: Make this service asynchronous

        templateEngine = templateEng;
        fromEmail = from;
        mailSender = mSender;
        userRegisteredUrl = userRegUrl;
        passwordRecoveryUrl = passRecoveryUrl;
    }

    @Override
    public final void sendPasswordRecoveryMessage(final String email, final String token) {
        final String recoveryUrl;
        final String passwordRecoveryEmailText;

        log.debug("Sending password recovery email to {}", email);

        recoveryUrl = generateRecoveryUrl(passwordRecoveryUrl, token);
        passwordRecoveryEmailText = generateEmailContent("mail/password-recovery", recoveryUrl);

        sendEmail(email, passwordRecoverySubject, passwordRecoveryEmailText);

        log.debug("Sent password recovery email to {}", email);
    }

    @Override
    public final void sendUserRegisteredMessage(final String email, final String token) {
        final String recoveryUrl;
        final String userRegisteredEmailText;

        log.debug("Sending user registered email to {}", email);

        recoveryUrl = generateRecoveryUrl(userRegisteredUrl, token);
        userRegisteredEmailText = generateEmailContent("mail/user-registered", recoveryUrl);

        sendEmail(email, userRegisteredSubject, userRegisteredEmailText);

        log.debug("Sent user registered email to {}", email);
    }

    private final String generateEmailContent(final String templateName, final String url) {
        final Context context;

        context = new Context();
        context.setVariable("url", url);
        return templateEngine.process(templateName, context);
    }

    private final String generateRecoveryUrl(final String baseUrl, final String token) {
        final String url;

        if (baseUrl.endsWith("/")) {
            url = baseUrl + token;
        } else {
            url = baseUrl + "/" + token;
        }

        return url;
    }

    private final void sendEmail(final String email, final String subject, final String emailText) {
        final MimeMessagePreparator messagePreparator;

        messagePreparator = mimeMessage -> {
            final MimeMessageHelper messageHelper;

            messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailText, true); // 'true' indicates HTML content
        };

        try {
            mailSender.send(messagePreparator);
        } catch (final Exception e) {
            log.error("Error sending email", e);
        }
    }
}
