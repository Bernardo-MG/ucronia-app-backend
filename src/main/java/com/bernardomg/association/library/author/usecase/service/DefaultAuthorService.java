
package com.bernardomg.association.library.author.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.usecase.validation.AuthorNameNotEmptyRule;
import com.bernardomg.association.library.author.usecase.validation.AuthorNameNotExistsRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultAuthorService implements AuthorService {

    private final AuthorRepository  authorRepository;

    private final Validator<Author> createAuthorValidator;

    public DefaultAuthorService(final AuthorRepository authorRepo) {
        super();

        authorRepository = Objects.requireNonNull(authorRepo);

        createAuthorValidator = new FieldRuleValidator<>(new AuthorNameNotEmptyRule(),
            new AuthorNameNotExistsRule(authorRepository));
    }

    @Override
    public final Author create(final Author author) {
        final Author toCreate;
        final Long   number;

        log.debug("Creating author {}", author);

        // Set number
        number = authorRepository.findNextNumber();

        toCreate = new Author(number, author.name());

        createAuthorValidator.validate(toCreate);

        return authorRepository.save(toCreate);
    }

    @Override
    public final void delete(final Long number) {

        log.debug("Deleting author {}", number);

        if (!authorRepository.exists(number)) {
            throw new MissingAuthorException(number);
        }

        authorRepository.delete(number);
    }

    @Override
    public final Iterable<Author> getAll(final Pageable pageable) {
        log.debug("Reading authors with pagination {}", pageable);

        return authorRepository.findAll(pageable);
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

        return author;
    }

    @Override
    public final Author update(final Author author) {
        final Author toCreate;
        final Long   number;

        log.debug("Creating author {}", author);

        if (!authorRepository.exists(author.number())) {
            throw new MissingAuthorException(author.number());
        }

        // Set number
        number = authorRepository.findNextNumber();

        toCreate = new Author(number, author.name());

        createAuthorValidator.validate(toCreate);

        return authorRepository.save(toCreate);
    }

}
