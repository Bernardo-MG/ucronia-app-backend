
package com.bernardomg.association.library.author.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class AuthorNameNotExistsRule implements FieldRule<Author> {

    private final AuthorRepository authorRepository;

    public AuthorNameNotExistsRule(final AuthorRepository authorRepo) {
        super();

        authorRepository = Objects.requireNonNull(authorRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Author author) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (authorRepository.existsByName(author.name())) {
            log.error("Existing author name {}", author.name());
            fieldFailure = FieldFailure.of("name", "existing", author.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
