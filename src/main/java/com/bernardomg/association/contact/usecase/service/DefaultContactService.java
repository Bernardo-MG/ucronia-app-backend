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

package com.bernardomg.association.contact.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.exception.MissingContactException;
import com.bernardomg.association.contact.domain.exception.MissingContactMethodException;
import com.bernardomg.association.contact.domain.filter.ContactQuery;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.domain.repository.ContactMethodRepository;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.usecase.validation.ContactIdentifierNotExistForAnotherRule;
import com.bernardomg.association.contact.usecase.validation.ContactIdentifierNotExistRule;
import com.bernardomg.association.contact.usecase.validation.ContactNameNotEmptyRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the contact service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultContactService implements ContactService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultContactService.class);

    private final ContactMethodRepository contactMethodRepository;

    private final ContactRepository       contactRepository;

    private final Validator<Contact>      createContactValidator;

    private final Validator<Contact>      patchContactValidator;

    private final Validator<Contact>      updateContactValidator;

    public DefaultContactService(final ContactRepository contactRepo, final ContactMethodRepository contactMethodRepo) {
        super();

        contactRepository = Objects.requireNonNull(contactRepo);
        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
        createContactValidator = new FieldRuleValidator<>(new ContactNameNotEmptyRule(),
            new ContactIdentifierNotExistRule(contactRepo));
        updateContactValidator = new FieldRuleValidator<>(new ContactNameNotEmptyRule(),
            new ContactIdentifierNotExistForAnotherRule(contactRepo));
        patchContactValidator = new FieldRuleValidator<>(new ContactNameNotEmptyRule(),
            new ContactIdentifierNotExistForAnotherRule(contactRepo));
    }

    @Override
    public final Contact create(final Contact contact) {
        final Contact toCreate;
        final Contact created;
        final Long    number;

        log.debug("Creating contact {}", contact);

        // TODO: maybe send an exception with all
        contact.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        // Set number
        number = contactRepository.findNextNumber();

        toCreate = new Contact(contact.identifier(), number, contact.name(), contact.birthDate(),
            contact.contactChannels());

        createContactValidator.validate(toCreate);

        created = contactRepository.save(toCreate);

        log.debug("Created contact {}", created);

        return created;
    }

    @Override
    public final Contact delete(final long number) {
        final Contact existing;

        log.debug("Deleting contact {}", number);

        existing = contactRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing contact {}", number);
                throw new MissingContactException(number);
            });

        contactRepository.delete(number);

        log.debug("Deleted contact {}", number);

        return existing;
    }

    @Override
    public final Page<Contact> getAll(final ContactQuery query, final Pagination pagination, final Sorting sorting) {
        final Page<Contact> read;

        log.debug("Reading contacts with query {}, pagination {} and sorting {}", query, pagination, sorting);

        read = contactRepository.findAll(query, pagination, sorting);

        log.debug("Read contacts with query {}, pagination {} and sorting {}: {}", query, pagination, sorting, read);

        return read;
    }

    @Override
    public final Optional<Contact> getOne(final long number) {
        final Optional<Contact> contact;

        log.debug("Reading contact {}", number);

        contact = contactRepository.findOne(number);
        if (contact.isEmpty()) {
            log.error("Missing contact {}", number);
            throw new MissingContactException(number);
        }

        log.debug("Read contact {}", contact);

        return contact;
    }

    @Override
    public final Contact patch(final Contact contact) {
        final Contact existing;
        final Contact toSave;
        final Contact saved;

        log.debug("Patching contact {} using data {}", contact.number(), contact);

        // TODO: Identificator must be unique or empty
        // TODO: Apply the creation validations

        existing = contactRepository.findOne(contact.number())
            .orElseThrow(() -> {
                log.error("Missing contact {}", contact.number());
                throw new MissingContactException(contact.number());
            });

        // TODO: maybe send an exception with all
        contact.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        toSave = copy(existing, contact);

        patchContactValidator.validate(toSave);

        saved = contactRepository.save(toSave);

        log.debug("Patched contact {}: {}", contact.number(), saved);

        return saved;
    }

    @Override
    public final Contact update(final Contact contact) {
        final Contact saved;

        log.debug("Updating contact {} using data {}", contact.number(), contact);

        // TODO: Identificator must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!contactRepository.exists(contact.number())) {
            log.error("Missing contact {}", contact.number());
            throw new MissingContactException(contact.number());
        }

        // TODO: maybe send an exception with all
        contact.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .forEach(this::checkContactMethodExists);

        updateContactValidator.validate(contact);

        saved = contactRepository.save(contact);

        log.debug("Updated contact {}: {}", contact.number(), saved);

        return saved;
    }

    private final void checkContactMethodExists(final ContactMethod contactMethod) {
        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Missing contact method {}", contactMethod.number());
            throw new MissingContactMethodException(contactMethod.number());
        }
    }

    private final Contact copy(final Contact existing, final Contact updated) {
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
        return new Contact(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            updated.contactChannels());
    }

}
