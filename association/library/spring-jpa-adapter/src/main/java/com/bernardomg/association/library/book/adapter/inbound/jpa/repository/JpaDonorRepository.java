
package com.bernardomg.association.library.book.adapter.inbound.jpa.repository;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.JpaProfileRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;

@Transactional
public final class JpaDonorRepository implements DonorRepository {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(JpaProfileRepository.class);

    private final ProfileSpringRepository profileSpringRepository;

    public JpaDonorRepository(final ProfileSpringRepository profileSpringRepo) {
        super();

        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if profile {} exists", number);

        exists = profileSpringRepository.existsByNumber(number);

        log.debug("Profile {} exists: {}", number, exists);

        return exists;
    }

}
