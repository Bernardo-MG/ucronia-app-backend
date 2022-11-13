
package com.bernardomg.security.password.test.service.unit;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.password.service.DefaultPasswordRecoveryService;
import com.bernardomg.security.password.service.PasswordRecoveryService;

@DisplayName("DefaultPasswordRecoveryService")
public class TestDefaultPasswordRecoveryService {

    private SecurityEmailSender     mailSender;

    private PasswordRecoveryService service;

    public TestDefaultPasswordRecoveryService() {
        super();
    }

    @Test
    @DisplayName("When recovering the password by email if there is no user then no email is sent")
    public final void testRecoverPasswordByEmail_NoUser_NoEmail() {
        initializeServiceNoUser();

        service.recoverPasswordByEmail("email@somewhere.com");

        Mockito.verify(mailSender, Mockito.never())
            .sendPasswordRecoveryEmail(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("When recovering the password by email if there is a user then a email is sent")
    public final void testRecoverPasswordByEmail_User_Email() {
        initializeServiceUser();

        service.recoverPasswordByEmail("email@somewhere.com");

        Mockito.verify(mailSender, Mockito.times(1))
            .sendPasswordRecoveryEmail(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("When recovering the username by email if there is no user then no email is sent")
    public final void testRecoverPasswordByUsername_NoUser_NoEmail() {
        initializeServiceNoUser();

        service.recoverPasswordByUsername("user");

        Mockito.verify(mailSender, Mockito.never())
            .sendPasswordRecoveryEmail(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("When recovering the username by email if there is a user then a email is sent")
    public final void testRecoverPasswordByUsername_User_Email() {
        initializeServiceUser();

        service.recoverPasswordByUsername("user");

        Mockito.verify(mailSender, Mockito.times(1))
            .sendPasswordRecoveryEmail(ArgumentMatchers.anyString());
    }

    private final void initializeServiceNoUser() {
        final UserRepository repository;

        repository = Mockito.mock(UserRepository.class);
        Mockito.when(repository.findOneByUsername(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());
        Mockito.when(repository.findOneByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());

        mailSender = Mockito.mock(SecurityEmailSender.class);

        service = new DefaultPasswordRecoveryService(repository, mailSender);
    }

    private final void initializeServiceUser() {
        final UserRepository repository;
        final PersistentUser user;

        repository = Mockito.mock(UserRepository.class);

        user = new PersistentUser();
        user.setUsername("user");
        user.setEmail("email@somewhere.com");

        Mockito.when(repository.findOneByUsername(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(user));
        Mockito.when(repository.findOneByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(user));

        mailSender = Mockito.mock(SecurityEmailSender.class);

        service = new DefaultPasswordRecoveryService(repository, mailSender);
    }

}
