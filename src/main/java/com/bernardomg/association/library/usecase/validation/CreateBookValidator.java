
package com.bernardomg.association.library.usecase.validation;

import java.util.List;

import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreateBookValidator extends AbstractFieldRuleValidator<Book> {

    public CreateBookValidator(final BookRepository bookRepository) {
        super(List.of(new BookTitleNotEmptyRule(), new BookLanguageCodeValidRule(),
            new BookIsbnNotExistsRule(bookRepository), new BookNoDuplicatedAuthorsRule()));
    }

}
