
package com.bernardomg.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.persistence.model.PersistentRole;
import com.bernardomg.security.persistence.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultRoleService implements RoleService {

    private final RoleRepository repository;

    @Override
    public final Iterable<? extends Privilege> addPrivileges(final Long id, final Iterable<Long> privileges) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Role create(final Role role) {
        final PersistentRole entity;
        final PersistentRole created;

        entity = toEntity(role);
        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<? extends Role> getAll(final Role sample, final Pageable pageable) {
        final PersistentRole entity;

        entity = toEntity(sample);
        return repository.findAll(Example.of(entity), pageable)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends Role> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<? extends Privilege> getPrivileges(final Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Role update(final Long id, final Role role) {
        final PersistentRole entity;
        final PersistentRole created;

        entity = toEntity(role);
        entity.setId(id);
        
        created = repository.save(entity);

        return toDto(created);
    }

    private final Role toDto(final PersistentRole entity) {
        final DtoRole data;

        data = new DtoRole();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentRole toEntity(final Role data) {
        final PersistentRole entity;

        entity = new PersistentRole();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

}
