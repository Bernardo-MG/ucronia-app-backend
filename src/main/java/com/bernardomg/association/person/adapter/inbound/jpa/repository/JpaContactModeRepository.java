
package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.model.ContactMode;
import com.bernardomg.association.person.domain.repository.ContactModeRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

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
    public void delete(final long number) {
        log.debug("Deleting contact mode {}", number);

        contactModeSpringRepository.deleteByNumber(number);

        log.debug("Deleted contact mode {}", number);
    }

    @Override
    public Iterable<ContactMode> findAll(final Pagination pagination, final Sorting sorting) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ContactMode save(final ContactMode person) {
        // TODO Auto-generated method stub
        return null;
    }

}
