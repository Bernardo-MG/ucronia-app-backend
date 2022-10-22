
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.model.Permission;

public final class DefaultSecurityPermissionService implements SecurityPermissionService {

    @Override
    public Iterable<? extends Permission> getAll(final Permission sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<? extends Permission> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

}
