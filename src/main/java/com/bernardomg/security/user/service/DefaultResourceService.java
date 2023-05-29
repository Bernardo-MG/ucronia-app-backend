
package com.bernardomg.security.user.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.model.ImmutableResource;
import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.model.request.ResourceQueryRequest;
import com.bernardomg.security.user.persistence.model.PersistentResource;
import com.bernardomg.security.user.persistence.repository.ResourceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultResourceService implements ResourceService {

    private final ResourceRepository repository;

    @Override
    public final Iterable<Resource> getAll(final ResourceQueryRequest sample, final Pageable pageable) {
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
        return ImmutableResource.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    private final PersistentResource toEntity(final ResourceQueryRequest data) {
        return PersistentResource.builder()
            .name(data.getName())
            .build();
    }

}
