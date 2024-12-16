
package com.bernardomg.association.library.author.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;

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
    public final void delete(final Long number) {
        log.debug("Deleting author {}", number);

        authorSpringRepository.deleteByNumber(number);

        log.debug("Deleted author {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if author {} exists", number);

        exists = authorSpringRepository.existsByNumber(number);

        log.debug("Author {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByName(final String name) {
        final boolean exists;

        log.debug("Checking if author {} exists", name);

        exists = authorSpringRepository.existsByName(name);

        log.debug("Author {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameForAnother(final String name, final Long number) {
        final boolean exists;

        log.debug("Checking if author {} exists for an author distinct from {}", name, number);

        exists = authorSpringRepository.existsByNotNumberAndName(number, name);

        log.debug("Author {} exists for an author distinct from {}: {}", name, number, exists);

        return exists;
    }

    @Override
    public final Iterable<Author> findAll(final Pagination pagination, final Sorting sorting) {
        final Iterable<Author> read;
        final Pageable         pageable;
        final Sort             sort;

        log.debug("Finding authors with pagination {} and sorting {}", pagination, sorting);

        sort = toSort(sorting);
        pageable = PageRequest.of(pagination.page(), pagination.size(), sort);
        read = authorSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found authors {}", read);

        return read;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the authors");

        number = authorSpringRepository.findNextNumber();

        log.debug("Found next number for the authors: {}", number);

        return number;
    }

    @Override
    public final Optional<Author> findOne(final Long number) {
        final Optional<Author> author;

        log.debug("Finding author with name {}", number);

        author = authorSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found author with name {}: {}", number, author);

        return author;
    }

    @Override
    public final Author save(final Author author) {
        final Optional<AuthorEntity> existing;
        final AuthorEntity           entity;
        final AuthorEntity           created;
        final Author                 saved;

        log.debug("Saving author {}", author);

        entity = toEntity(author);

        existing = authorSpringRepository.findByNumber(author.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = authorSpringRepository.save(entity);
        saved = toDomain(created);

        log.debug("Saved author {}", saved);

        return saved;
    }

    private final Author toDomain(final AuthorEntity entity) {
        return new Author(entity.getNumber(), entity.getName());
    }

    private final AuthorEntity toEntity(final Author domain) {
        return AuthorEntity.builder()
            .withNumber(domain.number())
            .withName(domain.name())
            .build();
    }

    private final Order toOrder(final Property property) {
        final Order order;

        if (Direction.ASC.equals(property.direction())) {
            order = Order.asc(property.name());
        } else {
            order = Order.desc(property.name());
        }

        return order;
    }

    private final Sort toSort(final Sorting sorting) {
        return Sort.by(sorting.properties()
            .stream()
            .map(this::toOrder)
            .toList());
    }

}
