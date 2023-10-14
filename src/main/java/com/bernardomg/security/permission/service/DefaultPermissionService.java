
package com.bernardomg.security.permission.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.permission.model.mapper.PermissionMapper;
import com.bernardomg.security.permission.persistence.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultPermissionService implements PermissionService {

    private final PermissionMapper     mapper;

    private final PermissionRepository repository;

    public DefaultPermissionService(final PermissionRepository repository, final PermissionMapper mapper) {
        super();

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public final Iterable<Permission> getAll(final Pageable pageable) {
        log.debug("Reading actions with pagination {}", pageable);

        return repository.findAll(pageable)
            .map(mapper::toDto);
    }

    @Override
    public final Optional<Permission> getOne(final long id) {

        log.debug("Reading action with id {}", id);

        return repository.findById(id)
            .map(mapper::toDto);
    }

}
