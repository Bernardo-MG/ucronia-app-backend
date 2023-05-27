
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoResource;
import com.bernardomg.security.data.model.Resource;
import com.bernardomg.security.data.persistence.model.PersistentResource;
import com.bernardomg.security.data.persistence.repository.ResourceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultResourceService implements ResourceService {

    private final ResourceRepository repository;

    @Override
    public final Iterable<Resource> getAll(final Resource sample, final Pageable pageable) {
        final PersistentResource entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<Resource> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    private final Resource toDto(final PersistentResource entity) {
        final DtoResource data;

        data = new DtoResource();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentResource toEntity(final Resource data) {
        final PersistentResource entity;

        entity = new PersistentResource();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

}
