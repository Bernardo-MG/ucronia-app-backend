
package com.bernardomg.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultChangePasswordService implements ChangePasswordService {

    private final UserRepository repository;
    
    private final PasswordEncoder passwordEncoder;

    @Override
    public final Boolean changePassword(final String username, final String password) {
        final PersistentUser entity;
        final String encoded;

        // TODO: validate
        // userUpdateValidator.validate(user);

        entity = repository.findOneByUsername(username)
            .get();
        
        encoded = passwordEncoder.encode(password);
        entity.setPassword(encoded);

        repository.save(entity);

        return true;
    }

}
