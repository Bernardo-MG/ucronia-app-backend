
package com.bernardomg.association.inventory.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.exception.MissingDonorException;
import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.usecase.validation.DonorNameNotEmptyRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultDonorService implements DonorService {

    private final Validator<Donor> createDonorValidator;

    private final DonorRepository  donorRepository;

    private final Validator<Donor> updateDonorValidator;

    public DefaultDonorService(final DonorRepository donorRepo) {
        super();

        donorRepository = Objects.requireNonNull(donorRepo);

        createDonorValidator = new FieldRuleValidator<>(new DonorNameNotEmptyRule());
        updateDonorValidator = new FieldRuleValidator<>(new DonorNameNotEmptyRule());
    }

    @Override
    public final Donor create(final Donor donor) {
        final Donor toCreate;
        final Long  number;

        log.debug("Creating donor {}", donor);

        createDonorValidator.validate(donor);

        // Set number
        number = donorRepository.findNextNumber();

        toCreate = Donor.builder()
            .withName(donor.getName())
            .withNumber(number)
            .build();

        return donorRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting donor {}", number);

        if (!donorRepository.exists(number)) {
            throw new MissingDonorException(number);
        }

        // TODO: Forbid deleting when there are relationships

        donorRepository.delete(number);
    }

    @Override
    public final Iterable<Donor> getAll(final Pageable pageable) {
        return donorRepository.findAll(pageable);
    }

    @Override
    public final Optional<Donor> getOne(final long number) {
        final Optional<Donor> donor;

        log.debug("Reading donor {}", number);

        donor = donorRepository.findOne(number);
        if (donor.isEmpty()) {
            throw new MissingDonorException(number);
        }

        return donor;
    }

    @Override
    public final Donor update(final Donor donor) {

        log.debug("Updating donor {} using data {}", donor.getNumber(), donor);

        if (!donorRepository.exists(donor.getNumber())) {
            throw new MissingDonorException(donor.getNumber());
        }

        updateDonorValidator.validate(donor);

        return donorRepository.save(donor);
    }

}
