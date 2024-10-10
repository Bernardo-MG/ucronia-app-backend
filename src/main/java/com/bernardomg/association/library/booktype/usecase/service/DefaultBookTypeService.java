
package com.bernardomg.association.library.booktype.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotEmptyRule;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotExistsRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultBookTypeService implements BookTypeService {

    private final BookTypeRepository  bookTypeRepository;

    private final Validator<BookType> createBookTypeValidator;

    public DefaultBookTypeService(final BookTypeRepository bookTypeRepo) {
        super();

        bookTypeRepository = Objects.requireNonNull(bookTypeRepo);

        createBookTypeValidator = new FieldRuleValidator<>(new BookTypeNameNotEmptyRule(),
            new BookTypeNameNotExistsRule(bookTypeRepository));
    }

    @Override
    public final BookType create(final BookType type) {
        final BookType toCreate;
        final Long     number;

        log.debug("Creating book type {}", type);

        // Set number
        number = bookTypeRepository.findNextNumber();

        toCreate = new BookType(number, type.name());

        createBookTypeValidator.validate(toCreate);

        return bookTypeRepository.save(toCreate);
    }

    @Override
    public final void delete(final Long number) {

        log.debug("Deleting book type {}", number);

        if (!bookTypeRepository.exists(number)) {
            throw new MissingBookTypeException(number);
        }

        bookTypeRepository.delete(number);
    }

    @Override
    public final Iterable<BookType> getAll(final Pageable pageable) {
        return bookTypeRepository.findAll(pageable);
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

        return bookType;
    }

}
