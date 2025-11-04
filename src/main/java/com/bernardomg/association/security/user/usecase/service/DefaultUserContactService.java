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

package com.bernardomg.association.security.user.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.exception.MissingContactException;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.security.user.domain.model.UserContact;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.association.security.user.usecase.validation.UserContactNameNotEmptyRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.user.domain.exception.MissingUsernameException;
import com.bernardomg.security.user.domain.model.User;
import com.bernardomg.security.user.domain.repository.UserRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

@Service
@Transactional
public final class DefaultUserContactService implements UserContactService {

    /**
     * Logger for the class.
     */
    private static final Logger          log = LoggerFactory.getLogger(DefaultUserContactService.class);

    private final Validator<UserContact> assignContactValidator;

    private final ContactRepository      contactRepository;

    private final UserContactRepository  userContactRepository;

    private final UserRepository         userRepository;

    public DefaultUserContactService(final UserRepository userRepo, final ContactRepository contactRepo,
            final UserContactRepository userContactRepo) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        contactRepository = Objects.requireNonNull(contactRepo);
        userContactRepository = Objects.requireNonNull(userContactRepo);

        assignContactValidator = new FieldRuleValidator<>(new UserContactNameNotEmptyRule(userContactRepository));
    }

    @Override
    public final Contact assignContact(final String username, final long contactNumber) {
        final User        readUser;
        final Contact     readContact;
        final UserContact userContact;

        log.debug("Assigning contact {} to {}", contactNumber, username);

        readUser = userRepository.findOne(username)
            .orElseThrow(() -> {
                log.error("Missing user {}", username);
                throw new MissingUsernameException(username);
            });

        readContact = contactRepository.findOne(contactNumber)
            .orElseThrow(() -> {
                log.error("Missing contact {}", contactNumber);
                throw new MissingContactException(contactNumber);
            });

        userContact = new UserContact(contactNumber, username);
        assignContactValidator.validate(userContact);

        userContactRepository.assignContact(readUser.username(), readContact.number());

        return readContact;
    }

    @Override
    public final Page<Contact> getAvailableContact(final Pagination pagination, final Sorting sorting) {
        final Page<Contact> contacts;

        log.trace("Reading all available contacts for pagination {} and sorting {}", pagination, sorting);

        contacts = userContactRepository.findAllNotAssigned(pagination, sorting);

        log.trace("Read all available contacts for pagination {} and sorting {}: {}", pagination, sorting, contacts);

        return contacts;
    }

    @Override
    public final Optional<Contact> getContact(final String username) {
        final Optional<Contact> contact;

        log.trace("Reading contact for {}", username);

        if (!userRepository.exists(username)) {
            log.error("Missing user {}", username);
            throw new MissingUsernameException(username);
        }

        contact = userContactRepository.findByUsername(username);

        log.trace("Read contact for {}: {}", username, contact);

        return contact;
    }

    @Override
    public final Contact unassignContact(final String username) {
        final boolean exists;
        final Contact contact;

        log.trace("Unassigning contact to {}", username);

        exists = userRepository.exists(username);
        if (!exists) {
            log.error("Missing user {}", username);
            throw new MissingUsernameException(username);
        }

        contact = userContactRepository.unassignContact(username);

        log.trace("Unassigned contact to {}", username);

        return contact;
    }

}
