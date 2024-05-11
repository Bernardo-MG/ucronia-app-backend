
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.GuestEntity;
import com.bernardomg.association.member.domain.model.Guest;
import com.bernardomg.association.member.domain.repository.GuestRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaGuestRepository implements GuestRepository {

    private final GuestSpringRepository guestSpringRepository;

    public JpaGuestRepository(final GuestSpringRepository guestSpringRepo) {
        super();

        guestSpringRepository = guestSpringRepo;
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting fee {}", number);

        guestSpringRepository.deleteByNumber(number);

        log.debug("Deleted fee {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if fee {} exists", number);

        exists = guestSpringRepository.existsByNumber(number);

        log.debug("Fee {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<Guest> findAll(final Pageable pageable) {
        final Page<Guest> guests;

        log.debug("Finding all the guests");

        guests = guestSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found all the guests: {}", guests);

        return guests;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the transactions");

        number = guestSpringRepository.findNextNumber();

        log.debug("Found next number for the transactions: {}", number);

        return number;
    }

    @Override
    public final Optional<Guest> findOne(final Long number) {
        final Optional<Guest> guest;

        log.debug("Finding guest with number {}", number);

        guest = guestSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found guest with number {}: {}", number, guest);

        return guest;
    }

    @Override
    public final Guest save(final Guest guest) {
        final Optional<GuestEntity> existing;
        final GuestEntity           entity;
        final GuestEntity           created;
        final Guest                 saved;

        log.debug("Saving guest {}", guest);

        entity = toEntity(guest);

        existing = guestSpringRepository.findByNumber(guest.getNumber());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = guestSpringRepository.save(entity);

        saved = guestSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved guest {}", saved);

        return saved;
    }

    private final Guest toDomain(final GuestEntity entity) {
        return Guest.builder()
            .withNumber(entity.getNumber())
            .withName(entity.getName())
            .build();
    }

    private final GuestEntity toEntity(final Guest data) {
        return GuestEntity.builder()
            .withNumber(data.getNumber())
            .withName(data.getName())
            .build();
    }

}
