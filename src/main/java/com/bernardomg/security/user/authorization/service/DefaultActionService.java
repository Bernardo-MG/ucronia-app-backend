
package com.bernardomg.security.user.authorization.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.authorization.persistence.model.PersistentAction;
import com.bernardomg.security.user.authorization.persistence.repository.ActionRepository;
import com.bernardomg.security.user.model.Action;
import com.bernardomg.security.user.model.mapper.ActionMapper;
import com.bernardomg.security.user.model.request.ActionQuery;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultActionService implements ActionService {

    private static final String    CACHE_MULTIPLE = "security_actions";

    private static final String    CACHE_SINGLE   = "security_action";

    private final ActionMapper     mapper;

    private final ActionRepository repository;

    @Override
    @PreAuthorize("hasAuthority('ACTION:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Action> getAll(final ActionQuery sample, final Pageable pageable) {
        final PersistentAction entitySample;

        entitySample = mapper.toEntity(sample);

        return repository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ACTION:READ')")
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Action> getOne(final long id) {
        return repository.findById(id)
            .map(mapper::toDto);
    }

}
