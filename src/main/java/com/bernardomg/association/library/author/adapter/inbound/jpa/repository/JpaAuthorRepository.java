
package com.bernardomg.association.library.author.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaAuthorRepository implements AuthorRepository {

    private final AuthorSpringRepository authorSpringRepository;

    public JpaAuthorRepository(final AuthorSpringRepository authorSpringRepo) {
        super();

        authorSpringRepository = Objects.requireNonNull(authorSpringRepo);
    }

    @Override
    public final void delete(final String name) {
        log.debug("Deleting author {}", name);

        authorSpringRepository.deleteByName(name);

        log.debug("Deleted author {}", name);
    }

    @Override
    public final boolean exists(final String name) {
        final boolean exists;

        log.debug("Checking if author {} exists", name);

        exists = authorSpringRepository.existsByName(name);

        log.debug("Author {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final Iterable<Author> findAll(final Pageable pageable) {
        final Page<AuthorEntity> page;
        final Iterable<Author>   read;

        log.debug("Finding authors with pagination {}", pageable);

        page = authorSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found authors {}", read);

        return read;
    }

    @Override
    public final Optional<Author> findOne(final String name) {
        final Optional<Author> author;

        log.debug("Finding author with name {}", name);

        author = authorSpringRepository.findByName(name)
            .map(this::toDomain);

        log.debug("Found author with name {}: {}", name, author);

        return author;
    }

    @Override
    public final boolean hasRelationships(final String name) {
        final boolean exists;

        log.debug("Checking if author {} has relationships", name);

        exists = authorSpringRepository.existsInBook(name);

        log.debug("Author {} has relationships: {}", name, exists);

        return exists;
    }

    @Override
    public final Author save(final Author author) {
        final AuthorEntity toCreate;
        final AuthorEntity created;
        final Author       saved;

        log.debug("Saving author {}", author);

        toCreate = toEntity(author);

        created = authorSpringRepository.save(toCreate);
        saved = toDomain(created);

        log.debug("Saved author {}", saved);

        return saved;
    }

    private final Author toDomain(final AuthorEntity entity) {
        return new Author(entity.getName());
    }

    private final AuthorEntity toEntity(final Author domain) {
        return AuthorEntity.builder()
            .withName(domain.name())
            .build();
    }

}
