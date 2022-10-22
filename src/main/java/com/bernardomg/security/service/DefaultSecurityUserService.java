
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.User;

@Service
public final class DefaultSecurityUserService implements SecurityUserService {

    @Override
    public User create(final User fee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean delete(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<? extends User> getAll(final User sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<? extends User> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public User update(final Long id, final User fee) {
        // TODO Auto-generated method stub
        return null;
    }

}
