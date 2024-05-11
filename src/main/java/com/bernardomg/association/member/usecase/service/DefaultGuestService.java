
package com.bernardomg.association.member.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingGuestException;
import com.bernardomg.association.member.domain.model.Guest;
import com.bernardomg.association.member.domain.repository.GuestRepository;
import com.bernardomg.association.member.usecase.validation.CreateGuestValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the guest service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
@Transactional
public final class DefaultGuestService implements GuestService {

    private final CreateGuestValidator createGuestValidator = new CreateGuestValidator();

    private final GuestRepository      guestRepository;

    public DefaultGuestService(final GuestRepository guestRepo) {
        super();

        guestRepository = Objects.requireNonNull(guestRepo);
    }

    @Override
    public final Guest create(final Guest guest) {
        final Guest toCreate;
        final Long  number;

        log.debug("Creating guest {}", guest);

        // Set number
        number = guestRepository.findNextNumber();

        toCreate = Guest.builder()
            .withName(guest.getName())
            .withNumber(number)
            .build();

        createGuestValidator.validate(toCreate);

        return guestRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting guest {}", number);

        if (!guestRepository.exists(number)) {
            throw new MissingGuestException(number);
        }

        // TODO: Forbid deleting when there are relationships

        guestRepository.delete(number);
    }

    @Override
    public final Iterable<Guest> getAll(final Pageable pageable) {
        log.debug("Reading guests with pagination {}", pageable);

        return guestRepository.findAll(pageable);
    }

    @Override
    public final Optional<Guest> getOne(final long number) {
        final Optional<Guest> guest;

        log.debug("Reading guest {}", number);

        guest = guestRepository.findOne(number);
        if (guest.isEmpty()) {
            throw new MissingGuestException(number);
        }

        return guest;
    }

    @Override
    public final Guest update(final Guest guest) {
        final Optional<Guest> existing;
        final Guest           toUpdate;

        log.debug("Updating guest {} using data {}", guest.getNumber(), guest);

        // TODO: Identificator and phone must be unique or empty
        // TODO: Apply the creation validations

        existing = guestRepository.findOne(guest.getNumber());
        if (existing.isEmpty()) {
            throw new MissingGuestException(guest.getNumber());
        }

        toUpdate = Guest.builder()
            .withNumber(guest.getNumber())
            .withName(guest.getName())
            .build();

        return guestRepository.save(toUpdate);
    }

}
