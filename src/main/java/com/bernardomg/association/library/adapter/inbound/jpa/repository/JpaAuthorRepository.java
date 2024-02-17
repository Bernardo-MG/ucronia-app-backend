
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.repository.AuthorRepository;

public final class JpaAuthorRepository implements AuthorRepository {

    private final AuthorSpringRepository authorSpringRepository;

    public JpaAuthorRepository(final AuthorSpringRepository authorSpringRepo) {
        super();

        authorSpringRepository = authorSpringRepo;
    }

    @Override
    public final boolean exists(final String name) {
        return authorSpringRepository.existsByName(name);
    }

    @Override
    public final Iterable<Author> findAll(final Pageable pageable) {
        return authorSpringRepository.findAll(pageable)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Author save(final Author book) {
        final AuthorEntity toCreate;
        final AuthorEntity created;

        toCreate = toEntity(book);
        created = authorSpringRepository.save(toCreate);

        return toDomain(created);
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
