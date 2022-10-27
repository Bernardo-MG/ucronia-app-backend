
package com.bernardomg.security.service;

import org.springframework.stereotype.Service;

import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.User;
import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.security.validation.register.RegisterUserValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultRegisterUserService implements RegisterUserService {

    private final UserRepository        repository;

    private final RegisterUserValidator validator;

    @Override
    public final User registerUser(final String username, final String email, final String password) {
        final PersistentUser entity;
        final PersistentUser created;
        final DtoUser        user;

        user = new DtoUser();
        user.setUsername(username);
        user.setEmail(email);

        validator.validate(user);

        entity = new PersistentUser();
        entity.setUsername(username);
        entity.setEmail(email);
        // TODO: Encode
        entity.setPassword(password);
        entity.setCredentialsExpired(false);
        entity.setEnabled(true);
        entity.setExpired(false);
        entity.setLocked(false);

        created = repository.save(entity);

        return toDto(created);
    }

    private final User toDto(final PersistentUser entity) {
        final DtoUser data;

        data = new DtoUser();
        data.setId(entity.getId());
        data.setUsername(entity.getUsername());
        data.setEmail(entity.getEmail());
        data.setCredentialsExpired(entity.getCredentialsExpired());
        data.setEnabled(entity.getEnabled());
        data.setExpired(entity.getExpired());
        data.setLocked(entity.getLocked());

        return data;
    }

}
