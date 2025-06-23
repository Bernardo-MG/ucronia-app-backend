
package com.bernardomg.association.library.author.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the donor has a name.
 */
public final class AuthorNameNotExistsForAnotherRule implements FieldRule<Author> {

    /**
     * Logger for the class.
     */
    private static final Logger    log = LoggerFactory.getLogger(AuthorNameNotExistsForAnotherRule.class);

    private final AuthorRepository authorRepository;

    public AuthorNameNotExistsForAnotherRule(final AuthorRepository authorRepo) {
        super();

        authorRepository = Objects.requireNonNull(authorRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Author author) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (authorRepository.existsByNameForAnother(author.name(), author.number())) {
            log.error("Existing author name {} for an author distinct from {}", author.name(), author.number());
            fieldFailure = new FieldFailure("existing", "name", author.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
