
package com.bernardomg.security.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.NonNull;

public final class DefaultSecurityEmailSender implements SecurityEmailSender {

    private final String         fromEmail;

    private final JavaMailSender mailSender;

    private final String         signUpSubject = "";

    private final String         signUpText    = "";

    public DefaultSecurityEmailSender(@NonNull final String from, @NonNull final JavaMailSender mSender) {
        super();

        fromEmail = from;
        mailSender = mSender;
    }

    @Override
    public final void sendSignUpEmail(final String username, final String email) {
        final SimpleMailMessage message;

        message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject(signUpSubject);
        message.setText(signUpText);
        mailSender.send(message);
    }

}
