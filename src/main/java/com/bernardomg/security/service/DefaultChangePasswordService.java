
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.security.validation.register.ChangePasswordPassValidator;
import com.bernardomg.security.validation.register.ChangePasswordValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultChangePasswordService implements ChangePasswordService {

    private final ChangePasswordPassValidator passValidator;

    private final PasswordEncoder             passwordEncoder;

    private final UserRepository              repository;

    private final ChangePasswordValidator     validator;

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
