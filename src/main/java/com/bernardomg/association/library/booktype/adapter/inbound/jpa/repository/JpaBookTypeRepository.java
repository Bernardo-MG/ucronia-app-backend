/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaBookTypeRepository implements BookTypeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger            log = LoggerFactory.getLogger(JpaBookTypeRepository.class);

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
    public final Page<BookType> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<BookType> read;
        final Pageable                                       pageable;

        log.debug("Finding book types with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = bookTypeSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found book types {}", read);

        return SpringPagination.toPage(read);
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
        final BookTypeEntity entity;

        entity = new BookTypeEntity();
        entity.setNumber(domain.number());
        entity.setName(domain.name());

        return entity;
    }

}
