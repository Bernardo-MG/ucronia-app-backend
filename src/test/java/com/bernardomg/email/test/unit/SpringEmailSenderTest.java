
package com.bernardomg.email.test.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Properties;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.bernardomg.email.sender.EmailSender;
import com.bernardomg.email.sender.SpringEmailSender;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringEmailSender")
public class SpringEmailSenderTest {

    @Captor
    private ArgumentCaptor<MimeMessagePreparator> emailMessageCaptor;

    @Mock
    private JavaMailSender                        javaMailSender;

    private final MimeMessage getMimeMessage() {
        final Session session;

        session = Mockito.mock(Session.class);
        given(session.getProperties()).willReturn(new Properties());
        return new MimeMessage(session);
    }

    private final EmailSender getSender() {
        return new SpringEmailSender("from@somewhere.com", javaMailSender);
    }

    @Test
    @DisplayName("The message is sent from the sender email")
    void testSendEmail_From() throws Exception {
        final MimeMessage mimeMessage;

        // WHEN
        getSender().sendEmail("email@somewhere.com", "subject", "content");

        // THEN
        verify(javaMailSender).send(emailMessageCaptor.capture());

        mimeMessage = getMimeMessage();
        emailMessageCaptor.getValue()
            .prepare(mimeMessage);

        Assertions.assertThat(mimeMessage.getFrom())
            .hasSize(1)
            .extracting(Object::toString)
            .contains("from@somewhere.com");
    }

    @Test
    @DisplayName("The message subject is the default one")
    void testSendEmail_Subject() throws Exception {
        final MimeMessage mimeMessage;

        // WHEN
        getSender().sendEmail("email@somewhere.com", "subject", "content");

        // THEN
        verify(javaMailSender).send(emailMessageCaptor.capture());

        mimeMessage = getMimeMessage();
        emailMessageCaptor.getValue()
            .prepare(mimeMessage);

        Assertions.assertThat(mimeMessage.getSubject())
            .isEqualTo("subject");
    }

    @Test
    @DisplayName("The message subject is sent to the target email")
    void testSendEmail_To() throws Exception {
        final MimeMessage mimeMessage;

        // WHEN
        getSender().sendEmail("email@somewhere.com", "subject", "content");

        // THEN
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
