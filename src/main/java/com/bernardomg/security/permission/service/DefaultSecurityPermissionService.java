
package com.bernardomg.security.permission.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.SecurityPermission;

public final class DefaultSecurityPermissionService implements SecurityPermissionService {

    @Override
    public Iterable<? extends SecurityPermission> getAll(final SecurityPermission sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<? extends SecurityPermission> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

}
