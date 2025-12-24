/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.guest.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.guest.domain.exception.MissingGuestException;
import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the guest service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultGuestService implements GuestService {

    /**
     * Logger for the class.
     */
    private static final Logger   log = LoggerFactory.getLogger(DefaultGuestService.class);

    private final GuestRepository guestRepository;

    public DefaultGuestService(final GuestRepository guestRepo) {
        super();

        guestRepository = Objects.requireNonNull(guestRepo);
    }

    @Override
    public final Guest create(final Guest guest) {
        final Guest toCreate;
        final Guest created;

        log.debug("Creating guest {}", guest);

        toCreate = new Guest(guest.identifier(), 0L, guest.name(), guest.birthDate(), guest.contactChannels(),
            guest.games(), guest.comments());

        created = guestRepository.save(toCreate);

        log.debug("Created guest {}", created);

        return created;
    }

    @Override
    public final Guest delete(final long number) {
        final Guest existing;

        log.debug("Deleting guest {}", number);

        existing = guestRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing guest {}", number);
                throw new MissingGuestException(number);
            });

        guestRepository.delete(number);

        log.debug("Deleted guest {}", number);

        return existing;
    }

    @Override
    public final Page<Guest> getAll(final GuestFilter filter, final Pagination pagination, final Sorting sorting) {
        final Page<Guest> guests;

        log.debug("Reading guests with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        guests = guestRepository.findAll(filter, pagination, sorting);

        log.debug("Read guests with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting, guests);

        return guests;
    }

    @Override
    public final Optional<Guest> getOne(final long number) {
        final Optional<Guest> guest;

        log.debug("Reading guest {}", number);

        guest = guestRepository.findOne(number);
        if (guest.isEmpty()) {
            log.error("Missing guest {}", number);
            throw new MissingGuestException(number);
        }

        log.debug("Read guest {}: {}", number, guest);

        return guest;
    }

    @Override
    public final Guest patch(final Guest guest) {
        final Guest existing;
        final Guest toSave;
        final Guest saved;

        log.debug("Patching guest {} using data {}", guest.number(), guest);

        existing = guestRepository.findOne(guest.number())
            .orElseThrow(() -> {
                log.error("Missing guest {}", guest.number());
                throw new MissingGuestException(guest.number());
            });

        toSave = copy(existing, guest);

        saved = guestRepository.save(toSave);

        log.debug("Patched guest {}: {}", guest.number(), saved);

        return saved;
    }

    @Override
    public final Guest update(final Guest guest) {
        final Guest saved;

        log.debug("Updating guest {} using data {}", guest.number(), guest);

        if (!guestRepository.exists(guest.number())) {
            log.error("Missing guest {}", guest.number());
            throw new MissingGuestException(guest.number());
        }

        saved = guestRepository.save(guest);

        log.debug("Updated guest {}: {}", guest.number(), saved);

        return saved;
    }

    private final Guest copy(final Guest existing, final Guest updated) {
        final ContactName name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new ContactName(Optional.ofNullable(updated.name()
                .firstName())
                .orElse(existing.name()
                    .firstName()),
                Optional.ofNullable(updated.name()
                    .lastName())
                    .orElse(existing.name()
                        .lastName()));
        }
        return new Guest(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            Optional.ofNullable(updated.contactChannels())
                .orElse(existing.contactChannels()),
            Optional.ofNullable(updated.games())
                .orElse(existing.games()),
            Optional.ofNullable(updated.comments())
                .orElse(existing.comments()));
    }

}
