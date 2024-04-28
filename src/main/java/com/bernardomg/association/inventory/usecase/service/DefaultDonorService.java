
package com.bernardomg.association.inventory.usecase.service;

import java.util.Objects;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.usecase.validation.CreateDonorValidator;

public final class DefaultDonorService implements DonorService {

    private final CreateDonorValidator createDonorValidator;

    private final DonorRepository      donorRepository;

    public DefaultDonorService(final DonorRepository donorRepo) {
        super();

        donorRepository = Objects.requireNonNull(donorRepo);

        createDonorValidator = new CreateDonorValidator(donorRepo);
    }

    @Override
    public final Donor create(final Donor donor) {
        createDonorValidator.validate(donor);

        return donorRepository.save(donor);
    }

}
