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

package com.bernardomg.association.person.usecase.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.model.Contact.Membership;
import com.bernardomg.association.person.domain.repository.ContactRepository;

@Service
@Transactional
public final class DefaultMemberStatusService implements MemberStatusService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultMemberStatusService.class);

    private final ContactRepository contactRepository;

    public DefaultMemberStatusService(final ContactRepository contactRepo) {
        super();

        contactRepository = Objects.requireNonNull(contactRepo);
    }

    @Override
    public final void activate(final YearMonth date, final Long contactNumber) {
        final Optional<Contact> contact;
        final Contact           activated;

        if (YearMonth.now()
            .equals(date)) {
            log.debug("Activating membership for {}", contactNumber);
            contact = contactRepository.findOne(contactNumber);

            if (contact.isEmpty()) {
                log.warn("Missing contact {}", contactNumber);
            } else {
                activated = activated(contact.get());
                contactRepository.save(activated);

                log.debug("Activated membership for {}", contactNumber);
            }
        }
    }

    @Override
    public final void applyRenewal() {
        final Collection<Contact> contacts;
        final Collection<Contact> toActivate;
        final Collection<Contact> toDeactivate;
        final Collection<Contact> toSave;

        log.debug("Applying membership renewals");

        contacts = contactRepository.findAllWithRenewalMismatch();

        toActivate = contacts.stream()
            .filter(p -> !p.membership()
                .get()
                .active())
            .map(this::activated)
            .toList();

        toDeactivate = contacts.stream()
            .filter(p -> p.membership()
                .get()
                .active())
            .map(this::deactivated)
            .toList();

        toSave = Stream.concat(toActivate.stream(), toDeactivate.stream())
            .toList();
        contactRepository.saveAll(toSave);
    }

    @Override
    public final void deactivate(final YearMonth date, final Long contactNumber) {
        final Optional<Contact> contact;
        final Contact           deactivated;

        // If deleting at the current month, the user is set to inactive
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Deactivating membership for {}", contactNumber);
            contact = contactRepository.findOne(contactNumber);

            if (contact.isEmpty()) {
                log.warn("Missing contact {}", contactNumber);
            } else {
                deactivated = deactivated(contact.get());
                contactRepository.save(deactivated);

                log.debug("Deactivated membership for {}", contactNumber);
            }
        }
    }

    private final Contact activated(final Contact original) {
        final Membership membership;

        membership = new Membership(true, true);
        return new Contact(original.identifier(), original.number(), original.name(), original.birthDate(),
            Optional.of(membership), original.contacts());
    }

    private final Contact deactivated(final Contact original) {
        final Membership membership;

        membership = new Membership(false, false);
        return new Contact(original.identifier(), original.number(), original.name(), original.birthDate(),
            Optional.of(membership), original.contacts());
    }

}
