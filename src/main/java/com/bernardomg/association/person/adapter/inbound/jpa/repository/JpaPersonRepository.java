
package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonContactMethodEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.specification.PersonSpecifications;
import com.bernardomg.association.person.domain.exception.MissingContactMethodException;
import com.bernardomg.association.person.domain.filter.PersonFilter;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.Membership;
import com.bernardomg.association.person.domain.model.Person.PersonContact;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaPersonRepository implements PersonRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaPersonRepository.class);

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final PersonSpringRepository        personSpringRepository;

    public JpaPersonRepository(final PersonSpringRepository personSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo) {
        super();

        personSpringRepository = Objects.requireNonNull(personSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
    }

    @Override
    public final void activate(final long number) {
        final Optional<PersonEntity> read;
        final PersonEntity           person;

        log.trace("Activating member {}", number);

        // TODO: throw an exception if it doesn't exist

        read = personSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            person = read.get();
            person.setActive(true);
            person.setRenewMembership(true);
            personSpringRepository.save(person);

            log.trace("Activated member {}", number);
        }
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting person {}", number);

        personSpringRepository.deleteByNumber(number);

        log.debug("Deleted person {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if person {} exists", number);

        exists = personSpringRepository.existsByNumber(number);

        log.debug("Person {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifier(final String identifier) {
        final boolean exists;

        log.debug("Checking if identifier {} exists", identifier);

        exists = personSpringRepository.existsByIdentifier(identifier);

        log.debug("Identifier {} exists: {}", identifier, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifierForAnother(final long number, final String identifier) {
        final boolean exists;

        log.debug("Checking if identifier {} exists for a person distinct from {}", identifier, number);

        exists = personSpringRepository.existsByIdentifierForAnother(number, identifier);

        log.debug("Identifier {} exists for a person distinct from {}: {}", identifier, number, exists);

        return exists;
    }

    @Override
    public final Page<Person> findAll(final PersonFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Person> read;
        final Pageable                                     pageable;
        final Optional<Specification<PersonEntity>>        spec;

        log.debug("Finding all the people");

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = PersonSpecifications.filter(filter);
        if (spec.isEmpty()) {
            read = personSpringRepository.findAll(pageable)
                .map(this::toDomain);
        } else {
            read = personSpringRepository.findAll(spec.get(), pageable)
                .map(this::toDomain);
        }

        log.debug("Found all the people: {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<Person> findAllToRenew() {
        final Collection<Person> persons;

        log.debug("Finding all the members to renew");

        persons = personSpringRepository.findAllByMemberTrueAndRenewMembershipTrue()
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all the members to renew: {}", persons);

        return persons;
    }

    @Override
    public final Collection<Person> findAllWithRenewalMismatch() {
        final Collection<Person> persons;

        log.debug("Finding all the people with a renewal mismatch");

        persons = personSpringRepository.findAllWithRenewalMismatch()
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all the people with a renewal mismatch: {}", persons);

        return persons;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the persons");

        number = personSpringRepository.findNextNumber();

        log.debug("Found next number for the persons: {}", number);

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
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = personSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
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

        // TODO: Why not returning the saved one?
        saved = personSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved person {}", saved);

        return saved;
    }

    @Override
    public Collection<Person> saveAll(final Collection<Person> persons) {
        final List<PersonEntity> entities;
        final List<Person>       saved;

        log.debug("Saving persons {}", persons);

        entities = persons.stream()
            .map(this::toEntity)
            .toList();

        saved = personSpringRepository.saveAll(entities)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Saved persons {}", saved);

        return saved;
    }

    private final ContactMethod toDomain(final ContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private final PersonContact toDomain(final PersonContactMethodEntity entity) {
        final ContactMethod method;

        method = toDomain(entity.getContactMethod());
        return new PersonContact(method, entity.getContact());
    }

    private final Person toDomain(final PersonEntity entity) {
        final PersonName                name;
        final Optional<Membership>      membership;
        final Collection<PersonContact> contacts;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        if (!entity.getMember()) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(entity.getActive(), entity.getRenewMembership()));
        }

        contacts = entity.getContacts()
            .stream()
            .map(this::toDomain)
            .toList();

        return new Person(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(), membership,
            contacts);
    }

    private final PersonEntity toEntity(final Person data) {
        final boolean                               member;
        final boolean                               active;
        final boolean                               renew;
        final PersonEntity                          entity;
        final Collection<PersonContactMethodEntity> contacts;

        if (data.membership()
            .isPresent()) {
            member = true;
            active = data.membership()
                .get()
                .active();
            renew = data.membership()
                .get()
                .renew();
        } else {
            member = false;
            active = true;
            renew = true;
        }

        entity = new PersonEntity();
        entity.setNumber(data.number());
        entity.setFirstName(data.name()
            .firstName());
        entity.setLastName(data.name()
            .lastName());
        entity.setIdentifier(data.identifier());
        entity.setBirthDate(data.birthDate());
        entity.setMember(member);
        entity.setActive(active);
        entity.setRenewMembership(renew);

        contacts = data.contacts()
            .stream()
            .map(c -> toEntity(entity, c))
            .toList();
        entity.setContacts(contacts);

        return entity;
    }

    private final PersonContactMethodEntity toEntity(final PersonEntity person, final PersonContact data) {
        final PersonContactMethodEntity     entity;
        final Optional<ContactMethodEntity> contactMethod;

        contactMethod = contactMethodSpringRepository.findByNumber(data.method()
            .number());

        if (contactMethod.isEmpty()) {
            throw new MissingContactMethodException(data.method()
                .number());
        }

        entity = new PersonContactMethodEntity();
        entity.setPerson(person);
        entity.setContactMethod(contactMethod.get());
        entity.setContact(data.contact());

        return entity;
    }

}
