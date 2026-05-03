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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.guest.domain.exception.GuestExistsException;
import com.bernardomg.association.guest.domain.exception.MissingGuestProfileException;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.model.Guest.Name;
import com.bernardomg.association.guest.domain.model.GuestProfile;
import com.bernardomg.association.guest.domain.model.GuestProfile.ContactChannel;
import com.bernardomg.association.guest.domain.repository.GuestProfileRepository;
import com.bernardomg.association.guest.domain.repository.GuestRepository;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the guest service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultProfileGuestService implements ProfileGuestService {

    /**
     * Logger for the class.
     */
    private static final Logger          log = LoggerFactory.getLogger(DefaultProfileGuestService.class);

    private final GuestRepository        guestRepository;

    private final GuestProfileRepository profileRepository;

    public DefaultProfileGuestService(final GuestRepository guestRepo, final GuestProfileRepository profileRepo) {
        super();

        guestRepository = Objects.requireNonNull(guestRepo);
        profileRepository = Objects.requireNonNull(profileRepo);
    }

    @Override
    public final Guest convertToGuest(final long number) {
        final GuestProfile                     existing;
        final Guest                            toCreate;
        final Guest                            created;
        final Collection<Guest.ContactChannel> contactChannels;
        final Name                             name;
        final Set<String>                      types;

        log.debug("Converting profile {} to guest", number);

        existing = profileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing profile {}", number);
                throw new MissingGuestProfileException(number);
            });

        if (guestRepository.exists(number)) {
            log.error("Guest {} already exists", number);
            throw new GuestExistsException(number);
        }

        contactChannels = existing.contactChannels()
            .stream()
            .map(this::toGuestContactChannel)
            .toList();
        name = new Name(existing.name()
            .firstName(),
            existing.name()
                .lastName());
        types = Stream.concat(existing.types()
            .stream(), Stream.of(Guest.PROFILE_TYPE))
            .collect(Collectors.toSet());
        toCreate = new Guest(existing.identifier(), existing.number(), name, existing.birthDate(), contactChannels,
            List.of(), existing.address(), existing.comments(), types);

        created = guestRepository.save(toCreate);

        log.debug("Converted profile {} to guest", number);

        return created;
    }

    private final Guest.ContactChannel toGuestContactChannel(final ContactChannel contactChannel) {
        final Guest.ContactMethod contactMethod;

        contactMethod = new Guest.ContactMethod(contactChannel.contactMethod()
            .number(),
            contactChannel.contactMethod()
                .name());
        return new Guest.ContactChannel(contactMethod, contactChannel.detail());
    }

}
