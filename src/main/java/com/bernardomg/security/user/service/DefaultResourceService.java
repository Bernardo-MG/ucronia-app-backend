
package com.bernardomg.security.user.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.model.mapper.ResourceMapper;
import com.bernardomg.security.user.model.request.ResourceQuery;
import com.bernardomg.security.user.persistence.model.PersistentResource;
import com.bernardomg.security.user.persistence.repository.ResourceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultResourceService implements ResourceService {

    private final ResourceMapper     mapper;

    private final ResourceRepository repository;

    @Override
    public final Iterable<Resource> getAll(final ResourceQuery sample, final Pageable pageable) {
        final PersistentResource entitySample;

        entitySample = mapper.toEntity(sample);

        return repository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    public final Optional<Resource> getOne(final Long id) {
        return repository.findById(id)
            .map(mapper::toDto);
    }

}
