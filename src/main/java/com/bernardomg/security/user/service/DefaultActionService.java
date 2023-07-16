
package com.bernardomg.security.user.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.model.Action;
import com.bernardomg.security.user.model.mapper.ActionMapper;
import com.bernardomg.security.user.model.request.ActionQuery;
import com.bernardomg.security.user.persistence.model.PersistentAction;
import com.bernardomg.security.user.persistence.repository.ActionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultActionService implements ActionService {

    private final ActionMapper     mapper;

    private final ActionRepository repository;

    @Override
    @PreAuthorize("hasAuthority('ACTION:READ')")
    public final Iterable<Action> getAll(final ActionQuery sample, final Pageable pageable) {
        final PersistentAction entitySample;

        entitySample = mapper.toEntity(sample);

        return repository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ACTION:READ')")
    public final Optional<Action> getOne(final long id) {
        return repository.findById(id)
            .map(mapper::toDto);
    }

}
