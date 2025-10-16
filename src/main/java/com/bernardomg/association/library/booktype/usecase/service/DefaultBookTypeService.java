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

package com.bernardomg.association.library.booktype.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotEmptyRule;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotExistsForAnotherRule;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotExistsRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

@Service
@Transactional
public final class DefaultBookTypeService implements BookTypeService {

    /**
     * Logger for the class.
     */
    private static final Logger       log = LoggerFactory.getLogger(DefaultBookTypeService.class);

    private final BookTypeRepository  bookTypeRepository;

    private final Validator<BookType> createBookTypeValidator;

    private final Validator<BookType> updateBookTypeValidator;

    public DefaultBookTypeService(final BookTypeRepository bookTypeRepo) {
        super();

        bookTypeRepository = Objects.requireNonNull(bookTypeRepo);

        createBookTypeValidator = new FieldRuleValidator<>(new BookTypeNameNotEmptyRule(),
            new BookTypeNameNotExistsRule(bookTypeRepository));
        updateBookTypeValidator = new FieldRuleValidator<>(new BookTypeNameNotEmptyRule(),
            new BookTypeNameNotExistsForAnotherRule(bookTypeRepository));
    }

    @Override
    public final BookType create(final BookType type) {
        final BookType toCreate;
        final BookType created;
        final Long     number;

        log.debug("Creating book type {}", type);

        // Set number
        number = bookTypeRepository.findNextNumber();

        toCreate = new BookType(number, type.name());

        createBookTypeValidator.validate(toCreate);

        created = bookTypeRepository.save(toCreate);

        log.debug("Created book type {}", type);

        return created;
    }

    @Override
    public final BookType delete(final Long number) {
        final BookType deleted;

        log.debug("Deleting book type {}", number);

        deleted = bookTypeRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing book type {}", number);
                throw new MissingBookTypeException(number);
            });

        bookTypeRepository.delete(number);

        log.debug("Deleted book type {}", number);

        return deleted;
    }

    @Override
    public final Page<BookType> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<BookType> books;

        log.debug("Reading book types with pagination {} and sorting {}", pagination, sorting);

        books = bookTypeRepository.findAll(pagination, sorting);

        log.debug("Read book types with pagination {} and sorting {}", pagination, sorting);

        return books;
    }

    @Override
    public final Optional<BookType> getOne(final Long number) {
        final Optional<BookType> bookType;

        log.debug("Reading book type {}", number);

        bookType = bookTypeRepository.findOne(number);
        if (bookType.isEmpty()) {
            log.error("Missing book type {}", number);
            throw new MissingBookTypeException(number);
        }

        log.debug("Read book type {}", number);

        return bookType;
    }

    @Override
    public final BookType update(final BookType type) {
        final BookType bookType;

        log.debug("Updating book type {}", type);

        if (!bookTypeRepository.exists(type.number())) {
            throw new MissingBookTypeException(type.number());
        }

        // Set number
        updateBookTypeValidator.validate(type);

        bookType = bookTypeRepository.save(type);

        log.debug("Updated book type {}", type);

        return bookType;
    }

}
