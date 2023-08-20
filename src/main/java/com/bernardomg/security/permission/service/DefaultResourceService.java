
package com.bernardomg.security.permission.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bernardomg.security.permission.persistence.model.PersistentResource;
import com.bernardomg.security.permission.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.model.mapper.ResourceMapper;
import com.bernardomg.security.user.model.request.ResourceQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultResourceService implements ResourceService {

    private static final String      CACHE_MULTIPLE = "security_resources";

    private static final String      CACHE_SINGLE   = "security_resource";

    private final ResourceMapper     mapper;

    private final ResourceRepository repository;

    public DefaultResourceService(final ResourceMapper mapper, final ResourceRepository repository) {
        super();

        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @PreAuthorize("hasAuthority('RESOURCE:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Resource> getAll(final ResourceQuery sample, final Pageable pageable) {
        final PersistentResource entitySample;

        log.debug("Reading resources with sample {} and pagination {}", sample, pageable);

        entitySample = mapper.toEntity(sample);

        return repository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('RESOURCE:READ')")
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Resource> getOne(final long id) {

        log.debug("Reading resource with id {}", id);

        return repository.findById(id)
            .map(mapper::toDto);
    }

}
