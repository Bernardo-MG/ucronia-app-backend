
package com.bernardomg.association.library.booktype.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotEmptyRule;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotExistsForAnotherRule;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotExistsRule;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultBookTypeService implements BookTypeService {

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
    public final void delete(final Long number) {

        log.debug("Deleting book type {}", number);

        if (!bookTypeRepository.exists(number)) {
            throw new MissingBookTypeException(number);
        }

        bookTypeRepository.delete(number);

        log.debug("Deleted book type {}", number);
    }

    @Override
    public final Iterable<BookType> getAll(final Pagination pagination, final Sorting sorting) {
        final Iterable<BookType> books;

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
