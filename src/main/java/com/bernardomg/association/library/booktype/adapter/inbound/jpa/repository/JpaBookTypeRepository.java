
package com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaBookTypeRepository implements BookTypeRepository {

    private final BookTypeSpringRepository bookTypeSpringRepository;

    public JpaBookTypeRepository(final BookTypeSpringRepository bookTypeSpringRepo) {
        super();

        bookTypeSpringRepository = Objects.requireNonNull(bookTypeSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting book type {}", number);

        bookTypeSpringRepository.deleteByNumber(number);

        log.debug("Deleted book type {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if book type {} exists", number);

        exists = bookTypeSpringRepository.existsByNumber(number);

        log.debug("Book type {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByName(final String name) {
        final boolean exists;

        log.debug("Checking if book type {} exists", name);

        exists = bookTypeSpringRepository.existsByName(name);

        log.debug("Book type {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameForAnother(final String name, final long number) {
        final boolean exists;

        log.debug("Checking if book type {} exists for a book type distinc from {}", name, number);

        exists = bookTypeSpringRepository.existsByNotNumberAndName(number, name);

        log.debug("Book type {} exists for a book type distinc from {}: {}", name, number, exists);

        return exists;
    }

    @Override
    public final Iterable<BookType> findAll(final Pagination pagination, final Sorting sorting) {
        final Iterable<BookType> read;
        final Pageable           pageable;

        log.debug("Finding book types with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = bookTypeSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found book types {}", read);

        return read;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the book types");

        number = bookTypeSpringRepository.findNextNumber();

        log.debug("Found next number for the book types: {}", number);

        return number;
    }

    @Override
    public final Optional<BookType> findOne(final long number) {
        final Optional<BookType> bookType;

        log.debug("Finding book type with name {}", number);

        bookType = bookTypeSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found book type with name {}: {}", number, bookType);

        return bookType;
    }

    @Override
    public final BookType save(final BookType bookType) {
        final Optional<BookTypeEntity> existing;
        final BookTypeEntity           entity;
        final BookTypeEntity           created;
        final BookType                 saved;

        log.debug("Saving book type {}", bookType);

        entity = toEntity(bookType);

        existing = bookTypeSpringRepository.findByNumber(bookType.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = bookTypeSpringRepository.save(entity);
        saved = toDomain(created);

        log.debug("Saved book type {}", saved);

        return saved;
    }

    private final BookType toDomain(final BookTypeEntity entity) {
        return new BookType(entity.getNumber(), entity.getName());
    }

    private final BookTypeEntity toEntity(final BookType domain) {
        return BookTypeEntity.builder()
            .withNumber(domain.number())
            .withName(domain.name())
            .build();
    }

}
