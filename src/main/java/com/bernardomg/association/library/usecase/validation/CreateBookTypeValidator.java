
package com.bernardomg.association.library.usecase.validation;

import java.util.List;

import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreateBookTypeValidator extends AbstractFieldRuleValidator<BookType> {

    public CreateBookTypeValidator(final BookTypeRepository bookTypeRepository) {
        super(List.of(new BookTypeNameNotEmptyRule(), new BookTypeNameNotExistsRule(bookTypeRepository)));
    }

}
