
package com.bernardomg.association.library.usecase.validation;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateBookValidator extends AbstractValidator<Book> {

    private final BookRepository bookRepository;

    public CreateBookValidator(final BookRepository bookRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
    }

    @Override
    protected final void checkRules(final Book book, final Collection<FieldFailure> failures) {
        FieldFailure failure;
        final long   uniqueAuthors;
        final int    totalAuthors;
        final long   duplicates;

        if (StringUtils.isBlank(book.getTitle())) {
            log.error("Empty title");
            failure = FieldFailure.of("title", "empty", book.getTitle());
            failures.add(failure);
        }

        if ((!StringUtils.isBlank(book.getIsbn())) && (bookRepository.existsByIsbn(book.getIsbn()))) {
            log.error("Existing ISBN {}", book.getIsbn());
            failure = FieldFailure.of("isbn", "existing", book.getIsbn());
            failures.add(failure);
        }

        uniqueAuthors = book.getAuthors()
            .stream()
            .map(Author::getName)
            .distinct()
            .count();
        totalAuthors = book.getAuthors()
            .size();
        if (uniqueAuthors < totalAuthors) {
            duplicates = (totalAuthors - uniqueAuthors);
            log.error("Received {} authors, but {} are duplicates", totalAuthors, duplicates);
            failure = FieldFailure.of("authors[]", "duplicated", duplicates);
            failures.add(failure);
        }
    }

}
