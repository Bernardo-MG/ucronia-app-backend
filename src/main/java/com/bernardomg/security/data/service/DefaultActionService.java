
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.Action;
import com.bernardomg.security.data.model.DtoAction;
import com.bernardomg.security.data.persistence.model.PersistentAction;
import com.bernardomg.security.data.persistence.repository.ActionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultActionService implements ActionService {

    private final ActionRepository repository;

    @Override
    public final Iterable<Action> getAll(final Action sample, final Pageable pageable) {
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
        final DtoAction data;

        data = new DtoAction();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentAction toEntity(final Action data) {
        final PersistentAction entity;

        entity = new PersistentAction();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

}
