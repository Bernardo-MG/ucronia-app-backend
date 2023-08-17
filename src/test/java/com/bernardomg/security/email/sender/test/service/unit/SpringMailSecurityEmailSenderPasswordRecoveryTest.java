
package com.bernardomg.security.email.sender.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Properties;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.email.sender.SpringMailSecurityEmailSender;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringMailSecurityEmailSender - Password recovery")
public class SpringMailSecurityEmailSenderPasswordRecoveryTest {

    private final ArgumentCaptor<MimeMessagePreparator> emailMessageCaptor = ArgumentCaptor
        .forClass(MimeMessagePreparator.class);

    @Mock
    private JavaMailSender                              javaMailSender;

    @Mock
    private SpringTemplateEngine                        templateEng;

    public SpringMailSecurityEmailSenderPasswordRecoveryTest() {
        super();
    }

    @BeforeEach
    public void initializeMailSender() {
        given(templateEng.process(ArgumentMatchers.anyString(), ArgumentMatchers.any(Context.class))).willReturn("");
    }

    private final MimeMessage getMimeMessage() {
        final Session session;

        session = Mockito.mock(Session.class);
        given(session.getProperties()).willReturn(new Properties());
        return new MimeMessage(session);
    }

    private final SecurityMessageSender getSender() {
        return new SpringMailSecurityEmailSender(templateEng, "sender@host.com", "http://somewhere.com",
            "http://somewhere.com", javaMailSender);
    }

    @Test
    @DisplayName("The message is sent from the sender email")
    void testSendEmail_From() throws Exception {
        final MimeMessage mimeMessage;

        getSender().sendPasswordRecoveryMessage("email@somewhere.com", "token");

        verify(javaMailSender).send(emailMessageCaptor.capture());

        mimeMessage = getMimeMessage();
        emailMessageCaptor.getValue()
            .prepare(mimeMessage);

        Assertions.assertThat(mimeMessage.getFrom())
            .hasSize(1)
            .extracting(Object::toString)
            .contains("sender@host.com");
    }

    @Test
    @DisplayName("The message subject is the default one")
    void testSendEmail_Subject() throws Exception {
        final MimeMessage mimeMessage;

        getSender().sendPasswordRecoveryMessage("email@somewhere.com", "token");

        verify(javaMailSender).send(emailMessageCaptor.capture());

        mimeMessage = getMimeMessage();
        emailMessageCaptor.getValue()
            .prepare(mimeMessage);

        Assertions.assertThat(mimeMessage.getSubject())
            .isEqualTo("Password recovery");
    }

    @Test
    @DisplayName("The message subject is sent to the target email")
    void testSendEmail_To() throws Exception {
        final MimeMessage mimeMessage;

        getSender().sendPasswordRecoveryMessage("email@somewhere.com", "token");

        verify(javaMailSender).send(emailMessageCaptor.capture());

        mimeMessage = getMimeMessage();
        emailMessageCaptor.getValue()
            .prepare(mimeMessage);

        Assertions.assertThat(mimeMessage.getAllRecipients())
            .extracting(Object::toString)
            .hasSize(1)
            .contains("email@somewhere.com");
    }

}
