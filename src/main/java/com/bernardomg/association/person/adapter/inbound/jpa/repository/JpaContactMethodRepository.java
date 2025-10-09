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

package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaContactMethodRepository implements ContactMethodRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaContactMethodRepository.class);

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    public JpaContactMethodRepository(final ContactMethodSpringRepository ContactMethodSpringRepository) {
        super();

        contactMethodSpringRepository = ContactMethodSpringRepository;
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting contact method {}", number);

        contactMethodSpringRepository.deleteByNumber(number);

        log.debug("Deleted contact method {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if fee {} exists", number);

        exists = contactMethodSpringRepository.existsByNumber(number);

        log.debug("Fee {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByName(final String name) {
        return contactMethodSpringRepository.existsByName(name);
    }

    @Override
    public final boolean existsByNameForAnother(final long number, final String name) {
        return contactMethodSpringRepository.existsByNameAndNumberNot(name, number);
    }

    @Override
    public final Page<ContactMethod> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<ContactMethod> read;
        final Pageable                                            pageable;

        log.debug("Finding all the contact methods");

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = contactMethodSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found all the contact methods: {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the contact methods");

        number = contactMethodSpringRepository.findNextNumber();

        log.debug("Found next number for the contact methods: {}", number);

        return number;
    }

    @Override
    public final Optional<ContactMethod> findOne(final Long number) {
        final Optional<ContactMethod> ContactMethod;

        log.debug("Finding contact method with number {}", number);

        ContactMethod = contactMethodSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found contact method with number {}: {}", number, ContactMethod);

        return ContactMethod;
    }

    @Override
    public final ContactMethod save(final ContactMethod person) {
        final Optional<ContactMethodEntity> existing;
        final ContactMethodEntity           entity;
        final ContactMethodEntity           created;
        final ContactMethod                 saved;

        log.debug("Saving person {}", person);

        entity = toEntity(person);

        existing = contactMethodSpringRepository.findByNumber(person.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = contactMethodSpringRepository.save(entity);

        // TODO: Why not returning the saved one?
        saved = contactMethodSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved person {}", saved);

        return saved;
    }

    private final ContactMethod toDomain(final ContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    private final ContactMethodEntity toEntity(final ContactMethod data) {
        final ContactMethodEntity entity;

        entity = new ContactMethodEntity();
        entity.setNumber(data.number());
        entity.setName(data.name());

        return entity;
    }

}
