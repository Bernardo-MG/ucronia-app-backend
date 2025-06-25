
package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaContactMethodRepository implements ContactMethodRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaContactMethodRepository.class);

    private final ContactMethodSpringRepository ContactMethodSpringRepository;

    public JpaContactMethodRepository(final ContactMethodSpringRepository ContactMethodSpringRepository) {
        super();

        this.ContactMethodSpringRepository = ContactMethodSpringRepository;
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting contact method {}", number);

        ContactMethodSpringRepository.deleteByNumber(number);

        log.debug("Deleted contact method {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if fee {} exists", number);

        exists = ContactMethodSpringRepository.existsByNumber(number);

        log.debug("Fee {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<ContactMethod> findAll(final Pagination pagination, final Sorting sorting) {
        final Page<ContactMethod> ContactMethods;
        final Pageable          pageable;

        log.debug("Finding all the contact methods");

        pageable = SpringPagination.toPageable(pagination, sorting);
        ContactMethods = ContactMethodSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found all the contact methods: {}", ContactMethods);

        return ContactMethods;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the contact methods");

        number = ContactMethodSpringRepository.findNextNumber();

        log.debug("Found next number for the contact methods: {}", number);

        return number;
    }

    @Override
    public final Optional<ContactMethod> findOne(final Long number) {
        final Optional<ContactMethod> ContactMethod;

        log.debug("Finding contact method with number {}", number);

        ContactMethod = ContactMethodSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found contact method with number {}: {}", number, ContactMethod);

        return ContactMethod;
    }

    @Override
    public final ContactMethod save(final ContactMethod person) {
        final Optional<ContactMethodEntity> existing;
        final ContactMethodEntity           entity;
        final ContactMethodEntity           created;
        final ContactMethod                 saved;

        log.debug("Saving person {}", person);

        entity = toEntity(person);

        existing = ContactMethodSpringRepository.findByNumber(person.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = ContactMethodSpringRepository.save(entity);

        // TODO: Why not returning the saved one?
        saved = ContactMethodSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved person {}", saved);

        return saved;
    }

    private final ContactMethod toDomain(final ContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private final ContactMethodEntity toEntity(final ContactMethod data) {
        final ContactMethodEntity entity;

        entity = new ContactMethodEntity();
        entity.setNumber(data.number());
        entity.setName(data.name());

        return entity;
    }

}
