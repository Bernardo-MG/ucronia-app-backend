
package com.bernardomg.association.library.usecase.validation;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

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

        bookRepository = bookRepo;
    }

    @Override
    protected final void checkRules(final Book book, final Collection<FieldFailure> failures) {
        FieldFailure failure;

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
    }

}
