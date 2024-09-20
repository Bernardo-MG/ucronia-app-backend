
package com.bernardomg.association.person.usecase.service;

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

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.usecase.validation.PersonNameNotEmptyRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the person service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
@Transactional
public final class DefaultPersonService implements PersonService {

    private final Validator<Person> createPersonValidator;

    private final PersonRepository  personRepository;

    public DefaultPersonService(final PersonRepository personRepo) {
        super();

        personRepository = Objects.requireNonNull(personRepo);
        createPersonValidator = new FieldRuleValidator<>(new PersonNameNotEmptyRule());
    }

    @Override
    public final Person create(final Person person) {
        final Person toCreate;
        final Long   number;

        log.debug("Creating person {}", person);

        // Set number
        number = personRepository.findNextNumber();

        toCreate = Person.builder()
            .withName(person.getName())
            .withNumber(number)
            .withIdentifier(person.getIdentifier())
            .withPhone(person.getPhone())
            .build();

        createPersonValidator.validate(toCreate);

        return personRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting person {}", number);

        if (!personRepository.exists(number)) {
            throw new MissingPersonException(number);
        }

        // TODO: Forbid deleting when there are relationships

        personRepository.delete(number);
    }

    @Override
    public final Iterable<Person> getAll(final Pageable pageable) {
        final Pageable pagination;

        log.debug("Reading persons with pagination {}", pageable);

        pagination = correctPagination(pageable);

        return personRepository.findAll(pagination);
    }

    @Override
    public final Optional<Person> getOne(final long number) {
        final Optional<Person> person;

        log.debug("Reading person {}", number);

        person = personRepository.findOne(number);
        if (person.isEmpty()) {
            log.error("Missing person {}", number);
            throw new MissingPersonException(number);
        }

        return person;
    }

    @Override
    public final Person update(final Person person) {
        log.debug("Updating person {} using data {}", person.getNumber(), person);

        // TODO: Identificator and phone must be unique or empty
        // TODO: Apply the creation validations

        if (!personRepository.exists(person.getNumber())) {
            log.error("Missing person {}", person.getNumber());
            throw new MissingPersonException(person.getNumber());
        }

        return personRepository.save(person);
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
