
package com.bernardomg.association.inventory.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.model.DonorName;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaDonorRepository implements DonorRepository {

    private final PersonSpringRepository personSpringRepository;

    public JpaDonorRepository(final PersonSpringRepository personSpringRepo) {
        super();

        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<PersonEntity> donor;

        log.debug("Deleting donor {}", number);

        donor = personSpringRepository.findByNumber(number);
        if (donor.isPresent()) {
            personSpringRepository.deleteById(donor.get()
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

        exists = personSpringRepository.existsByNumber(number);

        log.debug("Donor {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<Donor> findAll(final Pageable pageable) {
        final Iterable<Donor> read;

        log.debug("Finding transactions with pagination {}", pageable);

        read = personSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found transactions {}", read);

        return read;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the donors");

        number = personSpringRepository.findNextNumber();

        log.debug("Found next number for the donors: {}", number);

        return number;
    }

    @Override
    public final Optional<Donor> findOne(final long number) {
        final Optional<Donor> donor;

        log.debug("Finding donor with number {}", number);

        donor = personSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found donor with number {}: {}", number, donor);

        return donor;
    }

    @Override
    public final Donor save(final Donor donor) {
        final Optional<PersonEntity> existing;
        final PersonEntity           entity;
        final PersonEntity           created;
        final Donor                  saved;

        log.debug("Saving donor {}", donor);

        entity = toEntity(donor);

        existing = personSpringRepository.findByNumber(donor.getNumber());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = personSpringRepository.save(entity);
        saved = toDomain(created);

        log.debug("Saved donor {}", saved);

        return saved;
    }

    private final Donor toDomain(final PersonEntity donor) {
        final DonorName donorName;

        donorName = DonorName.builder()
            .withFirstName(donor.getName())
            .withLastName(donor.getSurname())
            .build();

        return Donor.builder()
            .withNumber(donor.getNumber())
            .withName(donorName)
            .build();
    }

    private final PersonEntity toEntity(final Donor donor) {
        return PersonEntity.builder()
            .withNumber(donor.getNumber())
            .withName(donor.getName()
                .getFirstName())
            .withSurname(donor.getName()
                .getLastName())
            .withPhone("")
            .withIdentifier("")
            .build();
    }

}
