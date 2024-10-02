
package com.bernardomg.association.inventory.usecase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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

        toCreate = new Donor(number, donor.name());

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
        final Pageable pagination;

        log.debug("Reading donors with pagination {}", pageable);

        pagination = correctPagination(pageable);

        return donorRepository.findAll(pagination);
    }

    @Override
    public final Optional<Donor> getOne(final long number) {
        final Optional<Donor> donor;

        log.debug("Reading donor {}", number);

        donor = donorRepository.findOne(number);
        if (donor.isEmpty()) {
            log.error("Missing donor {}", number);
            throw new MissingDonorException(number);
        }

        return donor;
    }

    @Override
    public final Donor update(final Donor donor) {

        log.debug("Updating donor {} using data {}", donor.number(), donor);

        if (!donorRepository.exists(donor.number())) {
            throw new MissingDonorException(donor.number());
        }

        updateDonorValidator.validate(donor);

        return donorRepository.save(donor);
    }

    private final Pageable correctPagination(final Pageable pageable) {
        final Sort     sort;
        final Pageable page;

        // TODO: change the pagination system
        sort = correctSort(pageable.getSort());

        if (pageable.isPaged()) {
            page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        } else {
            page = Pageable.unpaged(sort);
        }

        return page;
    }

    private final Sort correctSort(final Sort received) {
        final Optional<Order> fullNameOrder;
        final Optional<Order> numberOrder;
        final List<Order>     orders;
        final List<Order>     validOrders;

        // Full name
        fullNameOrder = received.stream()
            .filter(o -> "fullName".equals(o.getProperty()))
            .findFirst();

        orders = new ArrayList<>();
        if (fullNameOrder.isPresent()) {
            if (Direction.ASC.equals(fullNameOrder.get()
                .getDirection())) {
                orders.add(Order.asc("firstName"));
                orders.add(Order.asc("lastName"));
            } else {
                orders.add(Order.desc("firstName"));
                orders.add(Order.desc("lastName"));
            }
        }

        // Number
        numberOrder = received.stream()
            .filter(o -> "number".equals(o.getProperty()))
            .findFirst();
        if (numberOrder.isPresent()) {
            if (Direction.ASC.equals(numberOrder.get()
                .getDirection())) {
                orders.add(Order.asc("number"));
            } else {
                orders.add(Order.desc("number"));
            }
        }

        validOrders = received.stream()
            .filter(o -> !"fullName".equals(o.getProperty()))
            .filter(o -> !"number".equals(o.getProperty()))
            .toList();
        orders.addAll(validOrders);

        return Sort.by(orders);
    }

}
