
package com.bernardomg.association.inventory.usecase.service;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.usecase.validation.CreateDonorValidator;

@Transactional
public final class DefaultDonorService implements DonorService {

    private final CreateDonorValidator createDonorValidator;

    private final DonorRepository      donorRepository;

    private final CreateDonorValidator updateDonorValidator;

    public DefaultDonorService(final DonorRepository donorRepo) {
        super();

        donorRepository = Objects.requireNonNull(donorRepo);

        createDonorValidator = new CreateDonorValidator(donorRepo);
        updateDonorValidator = new CreateDonorValidator(donorRepo);
    }

    @Override
    public final Donor create(final Donor donor) {
        createDonorValidator.validate(donor);

        // TODO: this has two paths:
        // Save a member donor
        // Save a not member donor

        return donorRepository.save(donor);
    }

    @Override
    public Donor update(final Donor donor) {

        // TODO: validate it exists

        updateDonorValidator.validate(donor);

        // TODO: this has two paths:
        // Save a member donor
        // Save a not member donor

        return donorRepository.save(donor);
    }

}
