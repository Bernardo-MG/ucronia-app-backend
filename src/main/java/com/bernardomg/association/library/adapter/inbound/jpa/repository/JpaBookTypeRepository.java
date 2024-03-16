
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaBookTypeRepository implements BookTypeRepository {

    private final BookTypeSpringRepository bookTypeSpringRepository;

    public JpaBookTypeRepository(final BookTypeSpringRepository bookTypeSpringRepo) {
        super();

        bookTypeSpringRepository = bookTypeSpringRepo;
    }

    @Override
    public final void delete(final String name) {
        log.debug("Deleting book type {}", name);

        bookTypeSpringRepository.deleteByName(name);

        log.debug("Deleted book type {}", name);
    }

    @Override
    public final boolean exists(final String name) {
        final boolean exists;

        log.debug("Checking if book type {} exists", name);

        exists = bookTypeSpringRepository.existsByName(name);

        log.debug("Book type {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final Iterable<BookType> findAll(final Pageable pageable) {
        final Page<BookTypeEntity> page;
        final Iterable<BookType>   read;

        log.debug("Finding book types with pagination {}", pageable);

        page = bookTypeSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found book types {}", read);

        return read;
    }

    @Override
    public final Optional<BookType> getOne(final String name) {
        final Optional<BookType> bookType;

        log.debug("Finding book type with name {}", name);

        bookType = bookTypeSpringRepository.findOneByName(name)
            .map(this::toDomain);

        log.debug("Found book type with name {}: {}", name, bookType);

        return bookType;
    }

    @Override
    public final BookType save(final BookType bookType) {
        final BookTypeEntity toCreate;
        final BookTypeEntity created;
        final BookType       saved;

        log.debug("Saving book type {}", bookType);

        toCreate = toEntity(bookType);

        created = bookTypeSpringRepository.save(toCreate);

        saved = toDomain(created);

        log.debug("Saved book type {}", saved);

        return saved;
    }

    private final BookType toDomain(final BookTypeEntity entity) {
        return BookType.builder()
            .withName(entity.getName())
            .build();
    }

    private final BookTypeEntity toEntity(final BookType domain) {
        return BookTypeEntity.builder()
            .withName(domain.getName())
            .build();
    }

}
