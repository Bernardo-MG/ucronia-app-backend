
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.domain.repository.PublisherRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaPublisherRepository implements PublisherRepository {

    private final PublisherSpringRepository publisherSpringRepository;

    public JpaPublisherRepository(final PublisherSpringRepository publisherSpringRepo) {
        super();

        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
    }

    @Override
    public final void delete(final String name) {
        log.debug("Deleting publisher {}", name);

        publisherSpringRepository.deleteByName(name);

        log.debug("Deleted publisher {}", name);
    }

    @Override
    public final boolean exists(final String name) {
        final boolean exists;

        log.debug("Checking if publisher {} exists", name);

        exists = publisherSpringRepository.existsByName(name);

        log.debug("Publisher {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final Iterable<Publisher> getAll(final Pageable pageable) {
        final Page<PublisherEntity> page;
        final Iterable<Publisher>   read;

        log.debug("Finding publishers with pagination {}", pageable);

        page = publisherSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found publishers {}", read);

        return read;
    }

    @Override
    public final Optional<Publisher> getOne(final String name) {
        final Optional<Publisher> publisher;

        log.debug("Finding publisher with name {}", name);

        publisher = publisherSpringRepository.findByName(name)
            .map(this::toDomain);

        log.debug("Found publisher with name {}: {}", name, publisher);

        return publisher;
    }

    @Override
    public final boolean hasRelationships(final String name) {
        final boolean exists;

        log.debug("Checking if publisher {} has relationships", name);

        exists = publisherSpringRepository.existsInBook(name);

        log.debug("Publisher {} has relationships: {}", name, exists);

        return exists;
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
        return Publisher.builder()
            .withName(entity.getName())
            .build();
    }

    private final PublisherEntity toEntity(final Publisher domain) {
        return PublisherEntity.builder()
            .withName(domain.getName())
            .build();
    }

}
