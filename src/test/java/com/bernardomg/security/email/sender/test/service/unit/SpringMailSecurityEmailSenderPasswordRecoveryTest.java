
package com.bernardomg.security.email.sender.test.service.unit;

import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.email.sender.springframework.SpringMailSecurityEmailSender;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringMailSecurityEmailSender - Password recovery")
public class SpringMailSecurityEmailSenderPasswordRecoveryTest {

    private final ArgumentCaptor<SimpleMailMessage> emailMessageCaptor = ArgumentCaptor
        .forClass(SimpleMailMessage.class);

    @Mock
    private JavaMailSender                          javaMailSender;

    public SpringMailSecurityEmailSenderPasswordRecoveryTest() {
        super();
    }

    private final SecurityMessageSender getSender() {
        return new SpringMailSecurityEmailSender("sender@host.com", "http://somewhere.com", javaMailSender);
    }

    @Test
    @DisplayName("The message is sent from the sender email")
    void testSendEmail_From() {
        getSender().sendPasswordRecoveryEmail("email@somewhere.com", "token");

        verify(javaMailSender).send(emailMessageCaptor.capture());

        Assertions.assertThat(emailMessageCaptor.getValue()
            .getFrom())
            .isEqualTo("sender@host.com");
    }

    @Test
    @DisplayName("The message subject is the default one")
    void testSendEmail_Subject() {
        getSender().sendPasswordRecoveryEmail("email@somewhere.com", "token");

        verify(javaMailSender).send(emailMessageCaptor.capture());

        Assertions.assertThat(emailMessageCaptor.getValue()
            .getSubject())
            .isEqualTo("Password recovery");
    }

    @Test
    @DisplayName("The message text is built correctly")
    void testSendEmail_Text() {
        getSender().sendPasswordRecoveryEmail("email@somewhere.com", "token");

        verify(javaMailSender).send(emailMessageCaptor.capture());

        Assertions.assertThat(emailMessageCaptor.getValue()
            .getText())
            .contains("Visit http://somewhere.com/token to reset password");
    }

    @Test
    @DisplayName("The message subject is sent to the target email")
    void testSendEmail_To() {
        getSender().sendPasswordRecoveryEmail("email@somewhere.com", "token");

        verify(javaMailSender).send(emailMessageCaptor.capture());

        Assertions.assertThat(emailMessageCaptor.getValue()
            .getTo())
            .hasSize(1)
            .contains("email@somewhere.com");
    }

}
