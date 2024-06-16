
package com.bernardomg.association.library.usecase.validation;

import java.util.List;

import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreatePublisherValidator extends AbstractFieldRuleValidator<Publisher> {

    public CreatePublisherValidator(final PublisherRepository publisherRepository) {
        super(List.of(new PublisherNameNotEmptyRule(), new PublisherNameNotExistsRule(publisherRepository)));
    }

}
