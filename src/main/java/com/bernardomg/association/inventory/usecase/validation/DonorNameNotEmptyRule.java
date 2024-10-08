
package com.bernardomg.association.inventory.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class DonorNameNotEmptyRule implements FieldRule<Donor> {

    public DonorNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Donor donor) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(donor.name()
            .firstName())) {
            log.error("Empty name");
            fieldFailure = FieldFailure.of("name", "empty", donor.name()
                .firstName());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
