
package com.bernardomg.security.permission.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.mapper.ResourceMapper;
import com.bernardomg.security.permission.persistence.model.PersistentResource;
import com.bernardomg.security.permission.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.model.request.ResourceQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultResourceService implements ResourceService {

    private final ResourceMapper     mapper;

    private final ResourceRepository repository;

    public DefaultResourceService(final ResourceRepository repository, final ResourceMapper mapper) {
        super();

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public final Iterable<Resource> getAll(final ResourceQuery sample, final Pageable pageable) {
        final PersistentResource entitySample;

        log.debug("Reading resources with sample {} and pagination {}", sample, pageable);

        entitySample = mapper.toEntity(sample);

        return repository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    public final Optional<Resource> getOne(final long id) {

        log.debug("Reading resource with id {}", id);

        return repository.findById(id)
            .map(mapper::toDto);
    }

}
