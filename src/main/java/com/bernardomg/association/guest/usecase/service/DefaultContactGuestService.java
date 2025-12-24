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

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.exception.MissingContactException;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.guest.domain.exception.GuestExistsException;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;

/**
 * Default implementation of the guest service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultContactGuestService implements ContactGuestService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultContactGuestService.class);

    private final ContactRepository contactRepository;

    private final GuestRepository   guestRepository;

    public DefaultContactGuestService(final GuestRepository guestRepo, final ContactRepository contactRepo) {
        super();

        guestRepository = Objects.requireNonNull(guestRepo);
        contactRepository = Objects.requireNonNull(contactRepo);
    }

    @Override
    public final Guest convertToGuest(final long number) {
        final Contact existing;
        final Guest   toCreate;
        final Guest   created;

        log.debug("Converting contact {} to guest", number);

        existing = contactRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing contact {}", number);
                throw new MissingContactException(number);
            });

        if (guestRepository.exists(number)) {
            throw new GuestExistsException(number);
        }

        toCreate = new Guest(existing.identifier(), existing.number(), existing.name(), existing.birthDate(),
            existing.contactChannels(), List.of(), existing.comments());

        created = guestRepository.save(toCreate, number);

        log.debug("Converted contact {} to guest", number);

        return created;
    }

}
