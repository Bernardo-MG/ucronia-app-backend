
package com.bernardomg.association.organization.service;

import java.util.Optional;

import com.bernardomg.association.organization.model.Organization;

public interface OrganizationService {

    public Organization create(final Organization member);

    public Boolean delete(final Long id);

    public Iterable<? extends Organization> getAll();

    public Optional<? extends Organization> getOne(final Long id);

    public Organization update(final Long id, final Organization member);

}
