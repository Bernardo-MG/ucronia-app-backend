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

package com.bernardomg.association.guest.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntityMapper;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.UpdateGuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.UpdateGuestEntityMapper;
import com.bernardomg.association.guest.adapter.inbound.jpa.specification.GuestSpecifications;
import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaGuestRepository implements GuestRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaGuestRepository.class);

    private final ContactSpringRepository     contactSpringRepository;

    private final QueryGuestSpringRepository  queryGuestSpringRepository;

    private final UpdateGuestSpringRepository updateGuestSpringRepository;

    public JpaGuestRepository(final QueryGuestSpringRepository queryGuestSpringRepo,
            final UpdateGuestSpringRepository updateGuestSpringRepo, final ContactSpringRepository contactSpringRepo) {
        super();

        queryGuestSpringRepository = Objects.requireNonNull(queryGuestSpringRepo);
        updateGuestSpringRepository = Objects.requireNonNull(updateGuestSpringRepo);
        // TODO: remove contact repository
        contactSpringRepository = Objects.requireNonNull(contactSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member {}", number);

        // TODO: delete on cascade from the contact
        queryGuestSpringRepository.deleteByNumber(number);
        contactSpringRepository.deleteByNumber(number);

        log.debug("Deleted member {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if member {} exists", number);

        exists = queryGuestSpringRepository.existsByNumber(number);

        log.debug("Guest {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Guest> findAll(final GuestFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Guest> read;
        final Pageable                                    pageable;
        final Optional<Specification<QueryGuestEntity>>   spec;

        log.debug("Finding all the members with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = GuestSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = queryGuestSpringRepository.findAll(pageable)
                .map(QueryGuestEntityMapper::toDomain);
        } else {
            read = queryGuestSpringRepository.findAll(spec.get(), pageable)
                .map(QueryGuestEntityMapper::toDomain);
        }

        log.debug("Found all the members with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Guest> findOne(final Long number) {
        final Optional<Guest> member;

        log.trace("Finding member with number {}", number);

        member = queryGuestSpringRepository.findByNumber(number)
            .map(QueryGuestEntityMapper::toDomain);

        log.trace("Found member with number {}: {}", number, member);

        return member;
    }

    @Override
    public final Guest save(final Guest member) {
        final Optional<UpdateGuestEntity> existing;
        final UpdateGuestEntity           entity;
        final Guest                       created;
        final Long                        number;

        log.debug("Saving member {}", member);

        existing = updateGuestSpringRepository.findByNumber(member.number());
        if (existing.isPresent()) {
            entity = UpdateGuestEntityMapper.toEntity(existing.get(), member);
        } else {
            entity = UpdateGuestEntityMapper.toEntity(member);
            number = queryGuestSpringRepository.findNextNumber();
            entity.getContact()
                .setNumber(number);
        }

        setType(entity.getContact());

        created = UpdateGuestEntityMapper.toDomain(updateGuestSpringRepository.save(entity));

        log.debug("Saved member {}", created);

        return created;
    }

    @Override
    public final Guest save(final Guest member, final long number) {
        final UpdateGuestEntity       entity;
        final Guest                   created;
        final Optional<ContactEntity> contact;

        log.debug("Saving member {} with number {}", member, number);

        entity = UpdateGuestEntityMapper.toEntity(member);

        contact = contactSpringRepository.findByNumber(number);
        if (contact.isPresent()) {
            entity.setContact(contact.get());
        }

        setType(entity.getContact());

        created = UpdateGuestEntityMapper.toDomain(updateGuestSpringRepository.save(entity));

        log.debug("Saved member {} with number {}", created, number);

        return created;
    }

    @Override
    public final Collection<Guest> saveAll(final Collection<Guest> members) {
        final List<UpdateGuestEntity> entities;
        final List<Guest>             saved;
        final AtomicLong              number;

        log.debug("Saving members {}", members);

        number = new AtomicLong(queryGuestSpringRepository.findNextNumber());
        entities = members.stream()
            .map(m -> convert(m, number))
            .toList();

        entities.stream()
            .forEach(m -> setType(m.getContact()));

        saved = updateGuestSpringRepository.saveAll(entities)
            .stream()
            .map(UpdateGuestEntityMapper::toDomain)
            .toList();

        log.debug("Saved members {}", saved);

        return saved;
    }

    private final UpdateGuestEntity convert(final Guest member, final AtomicLong number) {
        final Optional<UpdateGuestEntity> existing;
        final UpdateGuestEntity           entity;

        existing = updateGuestSpringRepository.findByNumber(member.number());
        if (existing.isPresent()) {
            entity = UpdateGuestEntityMapper.toEntity(existing.get(), member);
        } else {
            entity = UpdateGuestEntityMapper.toEntity(member);
            entity.getContact()
                .setNumber(number.getAndIncrement());
        }

        return entity;
    }

    private final void setType(final ContactEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(List.of(GuestEntityConstants.CONTACT_TYPE));
        } else {
            entity.getTypes()
                .add(GuestEntityConstants.CONTACT_TYPE);
        }
    }

}
