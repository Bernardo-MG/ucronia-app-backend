
package com.bernardomg.association.library.author.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.domain.exception.AuthorHasRelationshipsException;
import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.usecase.validation.AuthorNameNotEmptyRule;
import com.bernardomg.association.library.author.usecase.validation.AuthorNameNotExistsRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.debug("Creating author {}", author);

        createAuthorValidator.validate(author);

        return authorRepository.save(author);
    }

    @Override
    public final void delete(final String name) {

        log.debug("Deleting author {}", name);

        if (!authorRepository.exists(name)) {
            throw new MissingAuthorException(name);
        }

        if (authorRepository.hasRelationships(name)) {
            throw new AuthorHasRelationshipsException(name);
        }

        authorRepository.delete(name);
    }

    @Override
    public final Iterable<Author> getAll(final Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public final Optional<Author> getOne(final String name) {
        final Optional<Author> author;

        log.debug("Reading author {}", name);

        author = authorRepository.findOne(name);
        if (author.isEmpty()) {
            throw new MissingAuthorException(name);
        }

        return author;
    }

}
