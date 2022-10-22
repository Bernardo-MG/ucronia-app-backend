
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.Privilege;

@Service
public final class DefaultPrivilegeService implements PrivilegeService {

    @Override
    public Iterable<? extends Privilege> getAll(final Privilege sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<? extends Privilege> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

}
