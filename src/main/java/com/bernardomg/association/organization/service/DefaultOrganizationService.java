
package com.bernardomg.association.organization.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.organization.model.DtoOrganization;
import com.bernardomg.association.organization.model.Organization;
import com.bernardomg.association.organization.model.PersistentOrganization;
import com.bernardomg.association.organization.repository.OrganizationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultOrganizationService implements OrganizationService {

    private final OrganizationRepository repository;

    @Override
    public final Organization create(final Organization organization) {
        final PersistentOrganization entity;
        final PersistentOrganization created;

        entity = toPersistentOrganization(organization);
        created = repository.save(entity);
        return toOrganization(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<? extends Organization> getAll() {
        return repository.findAll()
            .stream()
            .map(this::toOrganization)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends Organization> getOne(final Long id) {
        final Optional<PersistentOrganization> found;
        final Optional<? extends Organization> result;
        final Organization                     member;

        found = repository.findById(id);

        if (found.isPresent()) {
            member = toOrganization(found.get());
            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Organization update(final Long id, final Organization organization) {
        final PersistentOrganization entity;
        final PersistentOrganization updated;

        entity = toPersistentOrganization(organization);
        entity.setId(id);

        // TODO: It is returning the entity BEFORE the changes
        updated = repository.save(entity);
        return toOrganization(updated);
    }

    private final Organization toOrganization(final PersistentOrganization entity) {
        final DtoOrganization data;

        data = new DtoOrganization();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentOrganization toPersistentOrganization(final Organization data) {
        final PersistentOrganization entity;

        entity = new PersistentOrganization();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

}
