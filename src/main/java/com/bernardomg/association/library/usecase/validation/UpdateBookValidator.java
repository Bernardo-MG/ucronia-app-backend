
package com.bernardomg.association.library.usecase.validation;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.AbstractValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UpdateBookValidator extends AbstractValidator<Book> {

    private final BookRepository bookRepository;

    public UpdateBookValidator(final BookRepository bookRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
    }

    @Override
    protected final void checkRules(final Book book, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(book.getTitle())) {
            log.error("Empty title");
            failure = FieldFailure.of("title", "empty", book.getTitle());
            failures.add(failure);
        }

        if ((!StringUtils.isBlank(book.getIsbn())) && (bookRepository.existsByIsbn(book.getNumber(), book.getIsbn()))) {
            log.error("Existing ISBN {}", book.getIsbn());
            failure = FieldFailure.of("isbn", "existing", book.getIsbn());
            failures.add(failure);
        }
    }

}
