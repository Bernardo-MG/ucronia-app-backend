
package com.bernardomg.security.service;

import org.springframework.stereotype.Service;

import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultChangePasswordService implements ChangePasswordService {

    private final UserRepository repository;

    @Override
    public final Boolean changePassword(final String username, final String password) {
        final PersistentUser entity;

        // TODO: update
        // userUpdateValidator.validate(user);

        entity = repository.findOneByUsername(username)
            .get();
        
        // TODO: Encode
        entity.setPassword(password);

        repository.save(entity);

        return true;
    }

}
