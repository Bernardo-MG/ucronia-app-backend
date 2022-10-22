
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.Role;

@Service
public final class DefaultSecurityRoleService implements SecurityRoleService {

    @Override
    public Role create(final Role fee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean delete(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<? extends Role> getAll(final Role sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<? extends Role> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Role update(final Long id, final Role fee) {
        // TODO Auto-generated method stub
        return null;
    }

}
