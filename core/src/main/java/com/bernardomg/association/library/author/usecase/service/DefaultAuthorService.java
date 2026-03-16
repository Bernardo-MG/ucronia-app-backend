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

package com.bernardomg.association.library.author.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.usecase.validation.AuthorNameNotEmptyRule;
import com.bernardomg.association.library.author.usecase.validation.AuthorNameNotExistsForAnotherRule;
import com.bernardomg.association.library.author.usecase.validation.AuthorNameNotExistsRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

@Service
@Transactional
public final class DefaultAuthorService implements AuthorService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultAuthorService.class);

    private final AuthorRepository  authorRepository;

    private final Validator<Author> createAuthorValidator;

    private final Validator<Author> updateAuthorValidator;

    public DefaultAuthorService(final AuthorRepository authorRepo) {
        super();

        authorRepository = Objects.requireNonNull(authorRepo);

        createAuthorValidator = new FieldRuleValidator<>(new AuthorNameNotEmptyRule(),
            new AuthorNameNotExistsRule(authorRepository));
        updateAuthorValidator = new FieldRuleValidator<>(new AuthorNameNotEmptyRule(),
            new AuthorNameNotExistsForAnotherRule(authorRepository));
    }

    @Override
    public final Author create(final Author author) {
        final Author toCreate;
        final Author created;
        final Long   number;

        log.debug("Creating author {}", author);

        // Set number
        number = authorRepository.findNextNumber();

        toCreate = new Author(number, author.name());

        createAuthorValidator.validate(toCreate);

        created = authorRepository.save(toCreate);

        log.debug("Created author {}", author);

        return created;
    }

    @Override
    public final Author delete(final Long number) {
        final Author deleted;

        log.debug("Deleting author {}", number);

        deleted = authorRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing author {}", number);
                throw new MissingAuthorException(number);
            });

        authorRepository.delete(number);

        log.debug("Deleted author {}", number);

        return deleted;
    }

    @Override
    public final Page<Author> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<Author> authors;

        log.debug("Reading authors with pagination {} and sorting {}", pagination, sorting);

        authors = authorRepository.findAll(pagination, sorting);

        log.debug("Read authors with pagination {} and sorting {}", pagination, sorting);

        return authors;
    }

    @Override
    public final Optional<Author> getOne(final Long number) {
        final Optional<Author> author;

        log.debug("Reading author {}", number);

        author = authorRepository.findOne(number);
        if (author.isEmpty()) {
            log.error("Missing author {}", number);
            throw new MissingAuthorException(number);
        }

        log.debug("Read author {}", number);

        return author;
    }

    @Override
    public final Author update(final Author author) {
        final Author updated;

        log.debug("Updating author {}", author);

        if (!authorRepository.exists(author.number())) {
            throw new MissingAuthorException(author.number());
        }

        updateAuthorValidator.validate(author);

        updated = authorRepository.save(author);

        log.debug("Updated author {}", author);

        return updated;
    }

}
