
package com.bernardomg.association.inventory.usecase.validation;

import java.util.List;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreateDonorValidator extends AbstractFieldRuleValidator<Donor> {

    public CreateDonorValidator() {
        super(List.of(new DonorNameNotEmptyRule()));
    }

}
