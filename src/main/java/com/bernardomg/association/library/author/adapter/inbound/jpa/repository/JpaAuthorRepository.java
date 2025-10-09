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

package com.bernardomg.association.library.author.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntityMapper;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaAuthorRepository implements AuthorRepository {

    /**
     * Logger for the class.
     */
    private static final Logger          log = LoggerFactory.getLogger(JpaAuthorRepository.class);

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
    public final Page<Author> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Author> read;
        final Pageable                                     pageable;

        log.debug("Finding authors with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = authorSpringRepository.findAll(pageable)
            .map(AuthorEntityMapper::toDomain);

        log.debug("Found authors {}", read);

        return SpringPagination.toPage(read);
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
            .map(AuthorEntityMapper::toDomain);

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

        entity = AuthorEntityMapper.toEntity(author);

        existing = authorSpringRepository.findByNumber(author.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = authorSpringRepository.save(entity);
        saved = AuthorEntityMapper.toDomain(created);

        log.debug("Saved author {}", saved);

        return saved;
    }

}
