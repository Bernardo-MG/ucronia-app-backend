
package com.bernardomg.association.library.book.adapter.inbound.jpa.repository;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.domain.repository.DonorRepository;

@Transactional
public final class JpaDonorRepository implements DonorRepository {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(JpaDonorRepository.class);

    private final DonorSpringRepository donorSpringRepository;

    public JpaDonorRepository(final DonorSpringRepository donorSpringRepo) {
        super();

        donorSpringRepository = Objects.requireNonNull(donorSpringRepo);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if donor {} exists", number);

        exists = donorSpringRepository.existsByNumber(number);

        log.debug("Donor {} exists: {}", number, exists);

        return exists;
    }

}
