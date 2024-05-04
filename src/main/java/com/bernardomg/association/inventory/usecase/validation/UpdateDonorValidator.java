
package com.bernardomg.association.inventory.usecase.validation;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UpdateDonorValidator extends AbstractValidator<Donor> {

    private final DonorRepository donorRepository;

    public UpdateDonorValidator(final DonorRepository donorRepo) {
        super();

        donorRepository = Objects.requireNonNull(donorRepo);
    }

    @Override
    protected final void checkRules(final Donor donor, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        // Should have a name
        if (StringUtils.isBlank(donor.getName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", donor.getName());
            failures.add(failure);
        }

        // The name shouldn't exist
        if (donorRepository.existsByNameForAnother(donor.getName(), donor.getNumber())) {
            log.error("Existing name {}", donor.getName());
            failure = FieldFailure.of("name", "existing", donor.getName());
            failures.add(failure);
        }

        // The member shouldn't exist
        if ((donor.getMember()
            .getNumber() >= 0) && donorRepository.existsByMemberForAnother(donor.getMember()
                .getNumber(), donor.getNumber())) {
            log.error("Existing member {}", donor.getMember()
                .getNumber());
            failure = FieldFailure.of("member", "existing", donor.getMember()
                .getNumber());
            failures.add(failure);
        }
    }

}
