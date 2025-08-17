
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
    public final void delete(final Long number) {

        log.debug("Deleting author {}", number);

        if (!authorRepository.exists(number)) {
            throw new MissingAuthorException(number);
        }

        authorRepository.delete(number);

        log.debug("Deleted author {}", number);
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
