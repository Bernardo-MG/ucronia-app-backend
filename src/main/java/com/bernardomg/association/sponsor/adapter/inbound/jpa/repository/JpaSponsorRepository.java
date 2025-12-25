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

package com.bernardomg.association.sponsor.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.HashSet;
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
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.QuerySponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.QuerySponsorEntityMapper;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.UpdateSponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.UpdateSponsorEntityMapper;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.specification.SponsorSpecifications;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaSponsorRepository implements SponsorRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaSponsorRepository.class);

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final ContactSpringRepository       contactSpringRepository;

    private final QuerySponsorSpringRepository  querySponsorSpringRepository;

    private final UpdateSponsorSpringRepository updateSponsorSpringRepository;

    public JpaSponsorRepository(final QuerySponsorSpringRepository querySponsorSpringRepo,
            final UpdateSponsorSpringRepository updateSponsorSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo,
            final ContactSpringRepository contactSpringRepo) {
        super();

        querySponsorSpringRepository = Objects.requireNonNull(querySponsorSpringRepo);
        updateSponsorSpringRepository = Objects.requireNonNull(updateSponsorSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
        // TODO: remove contact repository
        contactSpringRepository = Objects.requireNonNull(contactSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting sponsor {}", number);

        // TODO: delete on cascade from the contact
        querySponsorSpringRepository.deleteByNumber(number);
        contactSpringRepository.deleteByNumber(number);

        log.debug("Deleted sponsor {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if sponsor {} exists", number);

        exists = querySponsorSpringRepository.existsByNumber(number);

        log.debug("Sponsor {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Sponsor> findAll(final SponsorFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Sponsor> read;
        final Pageable                                      pageable;
        final Optional<Specification<QuerySponsorEntity>>   spec;

        log.debug("Finding all the sponsors with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = SponsorSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = querySponsorSpringRepository.findAll(pageable)
                .map(QuerySponsorEntityMapper::toDomain);
        } else {
            read = querySponsorSpringRepository.findAll(spec.get(), pageable)
                .map(QuerySponsorEntityMapper::toDomain);
        }

        log.debug("Found all the sponsors with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            sorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Sponsor> findOne(final Long number) {
        final Optional<Sponsor> sponsor;

        log.trace("Finding sponsor with number {}", number);

        sponsor = querySponsorSpringRepository.findByNumber(number)
            .map(QuerySponsorEntityMapper::toDomain);

        log.trace("Found sponsor with number {}: {}", number, sponsor);

        return sponsor;
    }

    @Override
    public final Sponsor save(final Sponsor sponsor) {
        final Optional<UpdateSponsorEntity> existing;
        final UpdateSponsorEntity           entity;
        final Sponsor                       created;
        final List<Long>                    contactMethodNumbers;
        final List<ContactMethodEntity>     contactMethods;
        final Long                          number;

        log.debug("Saving sponsor {}", sponsor);

        existing = updateSponsorSpringRepository.findByNumber(sponsor.number());
        if (existing.isPresent()) {
            entity = UpdateSponsorEntityMapper.toEntity(existing.get(), sponsor);
        } else {
            contactMethodNumbers = sponsor.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = UpdateSponsorEntityMapper.toEntity(sponsor, contactMethods);
            number = querySponsorSpringRepository.findNextNumber();
            entity.getContact()
                .setNumber(number);
        }

        setType(entity.getContact());

        created = UpdateSponsorEntityMapper.toDomain(updateSponsorSpringRepository.save(entity));

        log.debug("Saved sponsor {}", created);

        return created;
    }

    @Override
    public final Sponsor save(final Sponsor sponsor, final long number) {
        final UpdateSponsorEntity       entity;
        final Sponsor                   created;
        final Optional<ContactEntity>   contact;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving sponsor {} with number {}", sponsor, number);

        contactMethodNumbers = sponsor.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = UpdateSponsorEntityMapper.toEntity(sponsor, contactMethods);

        contact = contactSpringRepository.findByNumber(number);
        if (contact.isPresent()) {
            entity.setContact(contact.get());
        }

        setType(entity.getContact());

        created = UpdateSponsorEntityMapper.toDomain(updateSponsorSpringRepository.save(entity));

        log.debug("Saved sponsor {} with number {}", created, number);

        return created;
    }

    @Override
    public final Collection<Sponsor> saveAll(final Collection<Sponsor> sponsors) {
        final List<UpdateSponsorEntity> entities;
        final List<Sponsor>             saved;
        final AtomicLong                number;

        log.debug("Saving sponsors {}", sponsors);

        number = new AtomicLong(querySponsorSpringRepository.findNextNumber());
        entities = sponsors.stream()
            .map(m -> convert(m, number))
            .toList();

        entities.stream()
            .forEach(m -> setType(m.getContact()));

        saved = updateSponsorSpringRepository.saveAll(entities)
            .stream()
            .map(UpdateSponsorEntityMapper::toDomain)
            .toList();

        log.debug("Saved sponsors {}", saved);

        return saved;
    }

    private final UpdateSponsorEntity convert(final Sponsor sponsor, final AtomicLong number) {
        final Optional<UpdateSponsorEntity> existing;
        final UpdateSponsorEntity           entity;
        final List<Long>                    contactMethodNumbers;
        final List<ContactMethodEntity>     contactMethods;

        existing = updateSponsorSpringRepository.findByNumber(sponsor.number());
        if (existing.isPresent()) {
            entity = UpdateSponsorEntityMapper.toEntity(existing.get(), sponsor);
        } else {
            contactMethodNumbers = sponsor.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = UpdateSponsorEntityMapper.toEntity(sponsor, contactMethods);
            entity.getContact()
                .setNumber(number.getAndIncrement());
        }

        return entity;
    }

    private final void setType(final ContactEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(List.of(SponsorEntityConstants.CONTACT_TYPE));
        } else {
            entity.setTypes(new HashSet<>(entity.getTypes()));
            entity.getTypes()
                .add(SponsorEntityConstants.CONTACT_TYPE);
        }
    }

}
