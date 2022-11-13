
package com.bernardomg.security.password.service;

import java.util.Arrays;
import java.util.Optional;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.validation.exception.ValidationException;

import lombok.NonNull;

public final class DefaultPasswordRecoveryService implements PasswordRecoveryService {

    private final SecurityEmailSender mailSender;

    private final UserRepository      repository;

    public DefaultPasswordRecoveryService(@NonNull final UserRepository repo,
            @NonNull final SecurityEmailSender mSender) {
        super();

        repository = repo;
        mailSender = mSender;
    }

    @Override
    public final Boolean startPasswordRecovery(final String email) {
        final Optional<PersistentUser> user;
        final Failure                  error;

        user = repository.findOneByEmail(email);
        if (!user.isPresent()) {
            error = FieldFailure.of("error.email.notExisting", "roleForm", "memberId", email);
            throw new ValidationException(Arrays.asList(error));
        }

        mailSender.sendPasswordRecoveryEmail(user.get()
            .getEmail());

        return true;
    }

}
