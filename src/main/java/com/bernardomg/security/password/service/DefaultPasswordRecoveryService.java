
package com.bernardomg.security.password.service;

import java.util.Optional;

import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;

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
    public final Boolean recoverPassword(final String email) {
        final Optional<PersistentUser> user;
        final Boolean                  recovered;

        user = repository.findOneByEmail(email);
        if (user.isPresent()) {
            mailSender.sendPasswordRecoveryEmail(user.get()
                .getEmail());
            recovered = true;
        } else {
            recovered = false;
        }

        return recovered;
    }

}
