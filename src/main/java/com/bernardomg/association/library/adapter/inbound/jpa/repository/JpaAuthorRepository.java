
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.repository.AuthorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaAuthorRepository implements AuthorRepository {

    private final AuthorSpringRepository authorSpringRepository;

    public JpaAuthorRepository(final AuthorSpringRepository authorSpringRepo) {
        super();

        authorSpringRepository = authorSpringRepo;
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
    public final Iterable<Author> getAll(final Pageable pageable) {
        final Page<AuthorEntity> page;
        final Iterable<Author>   read;

        log.debug("Finding authors with pagination {}", pageable);

        page = authorSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found authors {}", read);

        return read;
    }

    @Override
    public final Optional<Author> getOne(final String name) {
        final Optional<Author> author;

        log.debug("Finding author with name {}", name);

        author = authorSpringRepository.findOneByName(name)
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
        return Author.builder()
            .withName(entity.getName())
            .build();
    }

    private final AuthorEntity toEntity(final Author domain) {
        return AuthorEntity.builder()
            .withName(domain.getName())
            .build();
    }

}
