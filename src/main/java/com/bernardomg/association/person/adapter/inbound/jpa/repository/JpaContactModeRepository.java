
package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactModeEntity;
import com.bernardomg.association.person.domain.model.ContactMode;
import com.bernardomg.association.person.domain.repository.ContactModeRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaContactModeRepository implements ContactModeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaContactModeRepository.class);

    private final ContactModeSpringRepository contactModeSpringRepository;

    public JpaContactModeRepository(final ContactModeSpringRepository contactModeSpringRepository) {
        super();

        this.contactModeSpringRepository = contactModeSpringRepository;
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting contact mode {}", number);

        contactModeSpringRepository.deleteByNumber(number);

        log.debug("Deleted contact mode {}", number);
    }

    @Override
    public final Iterable<ContactMode> findAll(final Pagination pagination, final Sorting sorting) {
        final Page<ContactMode> contactModes;
        final Pageable          pageable;

        log.debug("Finding all the contact modes");

        pageable = SpringPagination.toPageable(pagination, sorting);
        contactModes = contactModeSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found all the contact modes: {}", contactModes);

        return contactModes;
    }

    @Override
    public final ContactMode save(final ContactMode person) {
        final Optional<ContactModeEntity> existing;
        final ContactModeEntity           entity;
        final ContactModeEntity           created;
        final ContactMode                 saved;

        log.debug("Saving person {}", person);

        entity = toEntity(person);

        existing = contactModeSpringRepository.findByNumber(person.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = contactModeSpringRepository.save(entity);

        // TODO: Why not returning the saved one?
        saved = contactModeSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved person {}", saved);

        return saved;
    }

    private final ContactMode toDomain(final ContactModeEntity entity) {
        return new ContactMode(entity.getNumber(), entity.getName());
    }

    private final ContactModeEntity toEntity(final ContactMode data) {
        final ContactModeEntity entity;

        entity = new ContactModeEntity();
        entity.setNumber(data.number());
        entity.setName(data.name());

        return entity;
    }

}
