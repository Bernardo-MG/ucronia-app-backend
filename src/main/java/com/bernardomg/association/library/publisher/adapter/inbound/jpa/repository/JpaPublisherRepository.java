
package com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Service
@Transactional
public final class JpaPublisherRepository implements PublisherRepository {

    /**
     * Logger for the class.
     */
    private static final Logger             log = LoggerFactory.getLogger(JpaPublisherRepository.class);

    private final PublisherSpringRepository publisherSpringRepository;

    public JpaPublisherRepository(final PublisherSpringRepository publisherSpringRepo) {
        super();

        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting publisher {}", number);

        publisherSpringRepository.deleteByNumber(number);

        log.debug("Deleted publisher {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if publisher {} exists", number);

        exists = publisherSpringRepository.existsByNumber(number);

        log.debug("Publisher {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByName(final String name) {
        final boolean exists;

        log.debug("Checking if publisher {} exists", name);

        exists = publisherSpringRepository.existsByName(name);

        log.debug("Publisher {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameForAnother(final String name, final Long number) {
        final boolean exists;

        log.debug("Checking if publisher {} exists for a publisher distinct from {}", name, number);

        exists = publisherSpringRepository.existsByNotNumberAndName(number, name);

        log.debug("Publisher {} exists for a publisher distinct from {}: {}", name, number, exists);

        return exists;
    }

    @Override
    public final Iterable<Publisher> findAll(final Pagination pagination, final Sorting sorting) {
        final Iterable<Publisher> read;
        final Pageable            pageable;

        log.debug("Finding publishers with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = publisherSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found publishers {}", read);

        return read;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the publishers");

        number = publisherSpringRepository.findNextNumber();

        log.debug("Found next number for the publishers: {}", number);

        return number;
    }

    @Override
    public final Optional<Publisher> findOne(final long number) {
        final Optional<Publisher> publisher;

        log.debug("Finding publisher with name {}", number);

        publisher = publisherSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found publisher with name {}: {}", number, publisher);

        return publisher;
    }

    @Override
    public final Publisher save(final Publisher publisher) {
        final Optional<PublisherEntity> existing;
        final PublisherEntity           entity;
        final PublisherEntity           created;
        final Publisher                 saved;

        log.debug("Saving publisher {}", publisher);

        entity = toEntity(publisher);

        existing = publisherSpringRepository.findByNumber(publisher.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = publisherSpringRepository.save(entity);
        saved = toDomain(created);

        log.debug("Saved publisher {}", saved);

        return saved;
    }

    private final Publisher toDomain(final PublisherEntity entity) {
        return new Publisher(entity.getNumber(), entity.getName());
    }

    private final PublisherEntity toEntity(final Publisher domain) {
        final PublisherEntity entity;

        entity = new PublisherEntity();
        entity.setNumber(domain.number());
        entity.setName(domain.name());

        return entity;
    }

}
