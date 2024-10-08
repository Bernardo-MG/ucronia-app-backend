
package com.bernardomg.association.library.booktype.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.booktype.domain.exception.BookTypeHasRelationshipsException;
import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotEmptyRule;
import com.bernardomg.association.library.booktype.usecase.validation.BookTypeNameNotExistsRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.debug("Creating book type {}", type);

        createBookTypeValidator.validate(type);

        return bookTypeRepository.save(type);
    }

    @Override
    public final void delete(final String name) {

        log.debug("Deleting book type {}", name);

        if (!bookTypeRepository.exists(name)) {
            throw new MissingBookTypeException(name);
        }

        if (bookTypeRepository.hasRelationships(name)) {
            throw new BookTypeHasRelationshipsException(name);
        }

        bookTypeRepository.delete(name);
    }

    @Override
    public final Iterable<BookType> getAll(final Pageable pageable) {
        return bookTypeRepository.findAll(pageable);
    }

    @Override
    public final Optional<BookType> getOne(final String name) {
        final Optional<BookType> bookType;

        log.debug("Reading book type {}", name);

        bookType = bookTypeRepository.findOne(name);
        if (bookType.isEmpty()) {
            log.error("Missing book type {}", name);
            throw new MissingBookTypeException(name);
        }

        return bookType;
    }

}
