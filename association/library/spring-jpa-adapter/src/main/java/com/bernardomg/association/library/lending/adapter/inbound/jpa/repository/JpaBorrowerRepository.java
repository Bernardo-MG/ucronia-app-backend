
package com.bernardomg.association.library.lending.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BorrowerEntityMapper;
import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.library.lending.domain.repository.BorrowerRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.JpaProfileRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;

@Transactional
public final class JpaBorrowerRepository implements BorrowerRepository {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(JpaProfileRepository.class);

    private final ProfileSpringRepository profileSpringRepository;

    public JpaBorrowerRepository(final ProfileSpringRepository profileSpringRepo) {
        super();

        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final Optional<Borrower> findOne(final Long number) {
        final Optional<Borrower> borrower;

        log.debug("Finding borrower with number {}", number);

        borrower = profileSpringRepository.findByNumber(number)
            .map(BorrowerEntityMapper::toDomain);

        log.debug("Found borrower with number {}: {}", number, borrower);

        return borrower;
    }

}
