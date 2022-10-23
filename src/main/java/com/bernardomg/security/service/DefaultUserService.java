
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.Role;
import com.bernardomg.security.model.User;

@Service
public final class DefaultUserService implements UserService {

    @Override
    public final Iterable<? extends Role> addRoles(final Long id, final Iterable<Long> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final User create(final User fee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Boolean delete(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Iterable<? extends User> getAll(final User sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Optional<? extends User> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public final Iterable<Role> getRoles(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final User update(final Long id, final User fee) {
        // TODO Auto-generated method stub
        return null;
    }

}
