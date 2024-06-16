
package com.bernardomg.association.inventory.usecase.validation;

import java.util.List;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class UpdateDonorValidator extends AbstractFieldRuleValidator<Donor> {

    public UpdateDonorValidator() {
        super(List.of(new DonorNameNotEmptyRule()));
    }

}
