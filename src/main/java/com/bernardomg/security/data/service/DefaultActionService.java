
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.Action;
import com.bernardomg.security.data.model.ImmutableAction;
import com.bernardomg.security.data.model.request.ActionQueryRequest;
import com.bernardomg.security.data.persistence.model.PersistentAction;
import com.bernardomg.security.data.persistence.repository.ActionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultActionService implements ActionService {

    private final ActionRepository repository;

    @Override
    public final Iterable<Action> getAll(final ActionQueryRequest sample, final Pageable pageable) {
        final PersistentAction entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<Action> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    private final Action toDto(final PersistentAction entity) {
        return ImmutableAction.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    private final PersistentAction toEntity(final ActionQueryRequest data) {
        return PersistentAction.builder()
            .name(data.getName())
            .build();
    }

}
