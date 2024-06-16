
package com.bernardomg.association.fee.usecase.validation;

import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreateFeeValidator extends AbstractFieldRuleValidator<Collection<Fee>> {

    public CreateFeeValidator(final PersonRepository personRepository, final FeeRepository feeRepository) {
        super(List.of(new FeeNoDuplicatedDatesRule(), new FeeDateNotRegisteredRule(personRepository, feeRepository)));
    }

}
