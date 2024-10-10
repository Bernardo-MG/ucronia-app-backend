
package com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class JpaPublisherRepository implements PublisherRepository {

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
    public final Iterable<Publisher> findAll(final Pageable pageable) {
        final Page<PublisherEntity> page;
        final Iterable<Publisher>   read;

        log.debug("Finding publishers with pagination {}", pageable);

        page = publisherSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

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
        final PublisherEntity toCreate;
        final PublisherEntity created;
        final Publisher       saved;

        log.debug("Saving publisher {}", publisher);

        toCreate = toEntity(publisher);

        created = publisherSpringRepository.save(toCreate);
        saved = toDomain(created);

        log.debug("Saved publisher {}", saved);

        return saved;
    }

    private final Publisher toDomain(final PublisherEntity entity) {
        return new Publisher(entity.getNumber(), entity.getName());
    }

    private final PublisherEntity toEntity(final Publisher domain) {
        return PublisherEntity.builder()
            .withNumber(domain.number())
            .withName(domain.name())
            .build();
    }

}
