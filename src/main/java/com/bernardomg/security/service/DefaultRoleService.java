
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.model.Role;

@Service
public final class DefaultRoleService implements RoleService {

    @Override
    public final Iterable<? extends Privilege> addPrivileges(final Long id, final Iterable<Long> privileges) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Role create(final Role fee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Boolean delete(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Iterable<? extends Role> getAll(final Role sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Optional<? extends Role> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public final Iterable<? extends Privilege> getPrivileges(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Role update(final Long id, final Role fee) {
        // TODO Auto-generated method stub
        return null;
    }

}
