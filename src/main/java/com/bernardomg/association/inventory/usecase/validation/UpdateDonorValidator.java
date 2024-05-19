
package com.bernardomg.association.inventory.usecase.validation;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UpdateDonorValidator extends AbstractValidator<Donor> {

    public UpdateDonorValidator() {
        super();
    }

    @Override
    protected final void checkRules(final Donor donor, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        // Should have a name
        if (StringUtils.isBlank(donor.getName()
            .getFirstName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", donor.getName()
                .getFirstName());
            failures.add(failure);
        }
    }

}
