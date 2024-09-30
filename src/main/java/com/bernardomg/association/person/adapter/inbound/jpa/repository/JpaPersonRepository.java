
package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaPersonRepository implements PersonRepository {

    private final PersonSpringRepository personSpringRepository;

    public JpaPersonRepository(final PersonSpringRepository personSpringRepo) {
        super();

        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting fee {}", number);

        personSpringRepository.deleteByNumber(number);

        log.debug("Deleted fee {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if fee {} exists", number);

        exists = personSpringRepository.existsByNumber(number);

        log.debug("Fee {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<Person> findAll(final Pageable pageable) {
        final Page<Person> persons;
        final Pageable     pagination;

        log.debug("Finding all the persons");

        pagination = correctPagination(pageable);

        persons = personSpringRepository.findAll(pagination)
            .map(this::toDomain);

        log.debug("Found all the persons: {}", persons);

        return persons;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the transactions");

        number = personSpringRepository.findNextNumber();

        log.debug("Found next number for the transactions: {}", number);

        return number;
    }

    @Override
    public final Optional<Person> findOne(final Long number) {
        final Optional<Person> person;

        log.debug("Finding person with number {}", number);

        person = personSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found person with number {}: {}", number, person);

        return person;
    }

    @Override
    public final Person save(final Person person) {
        final Optional<PersonEntity> existing;
        final PersonEntity           entity;
        final PersonEntity           created;
        final Person                 saved;

        log.debug("Saving person {}", person);

        entity = toEntity(person);

        existing = personSpringRepository.findByNumber(person.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = personSpringRepository.save(entity);

        saved = personSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved person {}", saved);

        return saved;
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

    private final Person toDomain(final PersonEntity entity) {
        final PersonName memberName;

        memberName = PersonName.builder()
            .withFirstName(entity.getFirstName())
            .withLastName(entity.getLastName())
            .build();
        return Person.builder()
            .withNumber(entity.getNumber())
            .withName(memberName)
            .withIdentifier(entity.getIdentifier())
            .withPhone(entity.getPhone())
            .build();
    }

    private final PersonEntity toEntity(final Person data) {
        return PersonEntity.builder()
            .withNumber(data.number())
            .withFirstName(data.name()
                .firstName())
            .withLastName(data.name()
                .lastName())
            .withIdentifier(data.identifier())
            .withPhone(data.phone())
            .build();
    }

}
