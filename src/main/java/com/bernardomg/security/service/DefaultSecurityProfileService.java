
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.Profile;

@Service
public final class DefaultSecurityProfileService implements SecurityProfileService {

    @Override
    public Profile create(final Profile fee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean delete(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<? extends Profile> getAll(final Profile sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<? extends Profile> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Profile update(final Long id, final Profile fee) {
        // TODO Auto-generated method stub
        return null;
    }

}
