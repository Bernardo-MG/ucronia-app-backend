
package com.bernardomg.association.library.usecase.validation;

import java.util.Optional;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class BookNoDuplicatedAuthorsRule implements FieldRule<Book> {

    public BookNoDuplicatedAuthorsRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Book book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final long                   uniqueAuthors;
        final int                    totalAuthors;
        final long                   duplicates;

        uniqueAuthors = book.getAuthors()
            .stream()
            .map(Author::getName)
            .distinct()
            .count();
        totalAuthors = book.getAuthors()
            .size();
        if (uniqueAuthors < totalAuthors) {
            // TODO: is this really an error? It can be corrected easily
            duplicates = (totalAuthors - uniqueAuthors);
            log.error("Received {} authors, but {} are duplicates", totalAuthors, duplicates);
            fieldFailure = FieldFailure.of("authors[]", "duplicated", duplicates);
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
