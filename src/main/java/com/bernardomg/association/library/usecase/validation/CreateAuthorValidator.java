
package com.bernardomg.association.library.usecase.validation;

import java.util.List;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreateAuthorValidator extends AbstractFieldRuleValidator<Author> {

    public CreateAuthorValidator(final AuthorRepository authorRepository) {
        super(List.of(new AuthorNameNotEmptyRule(), new AuthorNameNotExistsRule(authorRepository)));
    }

}
