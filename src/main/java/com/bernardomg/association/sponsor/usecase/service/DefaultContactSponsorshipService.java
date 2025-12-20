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

package com.bernardomg.association.sponsor.usecase.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.exception.MissingContactException;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.sponsor.domain.exception.SponsorExistsException;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;

/**
 * Default implementation of the sponsor service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultContactSponsorshipService implements ContactSponsorshipService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultContactSponsorshipService.class);

    private final ContactRepository contactRepository;

    private final SponsorRepository sponsorRepository;

    public DefaultContactSponsorshipService(final SponsorRepository sponsorRepo, final ContactRepository contactRepo) {
        super();

        sponsorRepository = Objects.requireNonNull(sponsorRepo);
        contactRepository = Objects.requireNonNull(contactRepo);
    }

    @Override
    public final Sponsor convertToSponsor(final long number) {
        final Contact existingContact;
        final Sponsor toCreate;
        final Sponsor created;

        log.debug("Converting contact {} to sponsor", number);

        existingContact = contactRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing contact {}", number);
                throw new MissingContactException(number);
            });

        if (sponsorRepository.exists(number)) {
            throw new SponsorExistsException(number);
        }

        toCreate = new Sponsor(existingContact.number(), existingContact.name(), List.of());

        created = sponsorRepository.save(toCreate, number);

        log.debug("Converted contact {} to sponsor", number);

        return created;
    }

}
