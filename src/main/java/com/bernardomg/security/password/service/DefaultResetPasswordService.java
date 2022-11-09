
package com.bernardomg.security.password.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.validation.ChangePasswordPassValidator;
import com.bernardomg.security.password.validation.ChangePasswordValidator;

public final class DefaultResetPasswordService implements ResetPasswordService {

    private final ChangePasswordPassValidator passValidator;

    private final PasswordEncoder             passwordEncoder;

    private final UserRepository              repository;

    private final ChangePasswordValidator     validator;

    public DefaultResetPasswordService(final UserRepository repository, final PasswordEncoder passwordEncoder,
            final ChangePasswordValidator validator, final ChangePasswordPassValidator passValidator) {
        super();

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.passValidator = passValidator;
    }

    @Override
    public final Boolean changePassword(final String username, final String password) {
        final Optional<PersistentUser> read;
        final PersistentUser           entity;
        final String                   encoded;
        final DtoUser                  user;

        user = new DtoUser();
        user.setUsername(username);

        validator.validate(user);
        passValidator.validate(password);

        read = repository.findOneByUsername(username);
        if (read.isPresent()) {
            entity = read.get();

            encoded = passwordEncoder.encode(password);
            entity.setPassword(encoded);

            repository.save(entity);
        }

        return true;
    }

}
