
package com.bernardomg.association.inventory.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.adapter.inbound.jpa.model.DonorEntity;
import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaDonorRepository implements DonorRepository {

    private final DonorSpringRepository donorSpringRepository;

    public JpaDonorRepository(final DonorSpringRepository donorSpringRepo) {
        super();

        donorSpringRepository = donorSpringRepo;
    }

    @Override
    public final void delete(final long number) {
        final Optional<DonorEntity> transaction;

        log.debug("Deleting donor {}", number);

        transaction = donorSpringRepository.findOneByNumber(number);
        if (transaction.isPresent()) {
            donorSpringRepository.deleteById(transaction.get()
                .getId());

            log.debug("Deleted donor {}", number);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete donor {} as it doesn't exist", number);
        }
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if donor {} exists", number);

        exists = donorSpringRepository.existsByNumber(number);

        log.debug("Donor {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsName(final String name) {
        final boolean exists;

        log.debug("Checking if donor name {} exists", name);

        exists = donorSpringRepository.existsByName(name);

        log.debug("Donor name {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsNameForAnother(final String name, final long number) {
        final boolean exists;

        log.debug("Checking if donor name {} for a donor distinct of {} exists", name, number);

        exists = donorSpringRepository.existsByNameAndNumberNot(name, number);

        log.debug("Donor name {} for a donor distinct of {} exists: {}", name, number, exists);

        return exists;
    }

    @Override
    public final Iterable<Donor> findAll(final Pageable pageable) {
        final Iterable<Donor> read;

        log.debug("Finding transactions with pagination {}", pageable);

        read = donorSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found transactions {}", read);

        return read;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the donors");

        number = donorSpringRepository.findNextNumber();

        log.debug("Found next number for the donors: {}", number);

        return number;
    }

    @Override
    public final Optional<Donor> findOne(final long number) {
        final Optional<Donor> donor;

        log.debug("Finding donor with number {}", number);

        donor = donorSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found donor with number {}: {}", number, donor);

        return donor;
    }

    @Override
    public final Donor save(final Donor donor) {
        final Optional<DonorEntity> existing;
        final DonorEntity           entity;
        final DonorEntity           created;
        final Donor                 saved;

        log.debug("Saving donor {}", donor);

        entity = toEntity(donor);

        existing = donorSpringRepository.findByNumber(donor.getNumber());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = donorSpringRepository.save(entity);

        saved = donorSpringRepository.findOneByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved donor {}", saved);

        return saved;
    }

    private final Donor toDomain(final DonorEntity donor) {
        final long member;

        if (donor.getMember() == null) {
            member = -1;
        } else {
            member = donor.getMember();
        }

        return Donor.builder()
            .withNumber(donor.getNumber())
            .withName(donor.getName())
            .withMember(member)
            .build();
    }

    private final DonorEntity toEntity(final Donor donor) {
        final Long member;

        if (donor.getMember() >= 0) {
            member = donor.getMember();
        } else {
            member = null;
        }

        return DonorEntity.builder()
            .withNumber(donor.getNumber())
            .withName(donor.getName())
            .withMember(member)
            .build();
    }

}
