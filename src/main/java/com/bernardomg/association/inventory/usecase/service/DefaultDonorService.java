
package com.bernardomg.association.inventory.usecase.service;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.exception.MissingDonorException;
import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.usecase.validation.CreateDonorValidator;
import com.bernardomg.association.inventory.usecase.validation.UpdateDonorValidator;

@Transactional
public final class DefaultDonorService implements DonorService {

    private final CreateDonorValidator createDonorValidator;

    private final DonorRepository      donorRepository;

    private final UpdateDonorValidator updateDonorValidator;

    public DefaultDonorService(final DonorRepository donorRepo) {
        super();

        donorRepository = Objects.requireNonNull(donorRepo);

        createDonorValidator = new CreateDonorValidator(donorRepo);
        updateDonorValidator = new UpdateDonorValidator(donorRepo);
    }

    @Override
    public final Donor create(final Donor donor) {
        final Donor toCreate;
        final Long  number;

        createDonorValidator.validate(donor);

        // Set number
        number = donorRepository.findNextNumber();

        // TODO: this has two paths:
        // Save a member donor
        // Save a not member donor

        toCreate = Donor.builder()
            .withName(donor.getName())
            .withMember(donor.getMember())
            .withNumber(number)
            .build();

        return donorRepository.save(toCreate);
    }

    @Override
    public final Donor update(final Donor donor) {
        final boolean exists;

        exists = donorRepository.exists(donor.getNumber());
        if (!exists) {
            throw new MissingDonorException(donor.getNumber());
        }

        updateDonorValidator.validate(donor);

        // TODO: this has two paths:
        // Save a member donor
        // Save a not member donor

        return donorRepository.save(donor);
    }

}
