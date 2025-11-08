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

package com.bernardomg.association.contact.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntityMapper;
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.specification.ContactSpecifications;
import com.bernardomg.association.contact.domain.filter.ContactQuery;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaContactRepository implements ContactRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaContactRepository.class);

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final ContactSpringRepository       contactSpringRepository;

    public JpaContactRepository(final ContactSpringRepository contactSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo) {
        super();

        contactSpringRepository = Objects.requireNonNull(contactSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting contact {}", number);

        contactSpringRepository.deleteByNumber(number);

        log.debug("Deleted contact {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if contact {} exists", number);

        exists = contactSpringRepository.existsByNumber(number);

        log.debug("Contact {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifier(final String identifier) {
        final boolean exists;

        log.debug("Checking if contact identifier {} exists", identifier);

        exists = contactSpringRepository.existsByIdentifier(identifier);

        log.debug("Contact identifier {} exists: {}", identifier, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifierForAnother(final long number, final String identifier) {
        final boolean exists;

        log.debug("Checking if identifier {} exists for a contact distinct from {}", identifier, number);

        exists = contactSpringRepository.existsByIdentifierForAnother(number, identifier);

        log.debug("Identifier {} exists for a contact distinct from {}: {}", identifier, number, exists);

        return exists;
    }

    @Override
    public final Page<Contact> findAll(final ContactQuery filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Contact> read;
        final Pageable                                      pageable;
        final Optional<Specification<ContactEntity>>        spec;

        log.debug("Finding all the contacts with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = ContactSpecifications.filter(filter);
        if (spec.isEmpty()) {
            read = contactSpringRepository.findAll(pageable)
                .map(ContactEntityMapper::toDomain);
        } else {
            read = contactSpringRepository.findAll(spec.get(), pageable)
                .map(ContactEntityMapper::toDomain);
        }

        log.debug("Found all the contacts with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            sorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the contacts");

        number = contactSpringRepository.findNextNumber();

        log.debug("Found next number for the contacts: {}", number);

        return number;
    }

    @Override
    public final Optional<Contact> findOne(final Long number) {
        final Optional<Contact> contact;

        log.debug("Finding contact with number {}", number);

        contact = contactSpringRepository.findByNumber(number)
            .map(ContactEntityMapper::toDomain);

        log.debug("Found contact with number {}: {}", number, contact);

        return contact;
    }

    @Override
    public final Contact save(final Contact contact) {
        final Optional<ContactEntity>   existing;
        final ContactEntity             entity;
        final Contact                   created;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving contact {}", contact);

        contactMethodNumbers = contact.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        // TODO: exception for missing contact methods
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = ContactEntityMapper.toEntity(contact, contactMethods);

        existing = contactSpringRepository.findByNumber(contact.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = ContactEntityMapper.toDomain(contactSpringRepository.save(entity));

        log.debug("Saved contact {}", created);

        return created;
    }

    @Override
    public final Collection<Contact> saveAll(final Collection<Contact> contacts) {
        final List<ContactEntity>       entities;
        final List<Contact>             saved;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving contacts {}", contacts);

        contactMethodNumbers = contacts.stream()
            .map(Contact::contactChannels)
            .flatMap(Collection::stream)
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        // TODO: exception for missing contact methods
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);

        entities = contacts.stream()
            .map(c -> ContactEntityMapper.toEntity(c, contactMethods))
            .toList();

        saved = contactSpringRepository.saveAll(entities)
            .stream()
            .map(ContactEntityMapper::toDomain)
            .toList();

        log.debug("Saved contacts {}", saved);

        return saved;
    }

}
