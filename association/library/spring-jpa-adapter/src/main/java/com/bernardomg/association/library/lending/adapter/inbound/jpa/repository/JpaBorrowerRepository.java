
package com.bernardomg.association.library.lending.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BorrowerEntityMapper;
import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.library.lending.domain.repository.BorrowerRepository;

@Transactional
public final class JpaBorrowerRepository implements BorrowerRepository {

    /**
     * Logger for the class.
     */
    private static final Logger            log = LoggerFactory.getLogger(JpaBorrowerRepository.class);

    private final BorrowerSpringRepository borrowerSpringRepository;

    public JpaBorrowerRepository(final BorrowerSpringRepository borrowerSpringRepo) {
        super();

        borrowerSpringRepository = Objects.requireNonNull(borrowerSpringRepo);
    }

    @Override
    public final Optional<Borrower> findOne(final Long number) {
        final Optional<Borrower> borrower;

        log.debug("Finding borrower with number {}", number);

        borrower = borrowerSpringRepository.findByNumber(number)
            .map(BorrowerEntityMapper::toDomain);

        log.debug("Found borrower with number {}: {}", number, borrower);

        return borrower;
    }

}
