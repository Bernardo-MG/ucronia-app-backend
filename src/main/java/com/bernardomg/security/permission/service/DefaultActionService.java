
package com.bernardomg.security.permission.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.mapper.ActionMapper;
import com.bernardomg.security.permission.persistence.model.PersistentAction;
import com.bernardomg.security.permission.persistence.repository.ActionRepository;
import com.bernardomg.security.user.model.Action;
import com.bernardomg.security.user.model.request.ActionQuery;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultActionService implements ActionService {

    private static final String    CACHE_MULTIPLE = "security_actions";

    private static final String    CACHE_SINGLE   = "security_action";

    private final ActionMapper     mapper;

    private final ActionRepository repository;

    public DefaultActionService(final ActionRepository repository, final ActionMapper mapper) {
        super();

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Action> getAll(final ActionQuery sample, final Pageable pageable) {
        final PersistentAction entitySample;

        log.debug("Reading actions with sample {} and pagination {}", sample, pageable);

        entitySample = mapper.toEntity(sample);

        return repository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Action> getOne(final long id) {

        log.debug("Reading action with id {}", id);

        return repository.findById(id)
            .map(mapper::toDto);
    }

}
