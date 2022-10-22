
package com.bernardomg.security.profile.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.profile.model.SecurityProfile;
import com.bernardomg.security.profile.model.SecurityProfileForm;

public final class DefaultSecurityProfileService implements SecurityProfileService {

    @Override
    public SecurityProfile create(final SecurityProfileForm fee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean delete(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<? extends SecurityProfile> getAll(final SecurityProfile sample, final Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<? extends SecurityProfile> getOne(final Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public SecurityProfile update(final Long id, final SecurityProfileForm fee) {
        // TODO Auto-generated method stub
        return null;
    }

}
