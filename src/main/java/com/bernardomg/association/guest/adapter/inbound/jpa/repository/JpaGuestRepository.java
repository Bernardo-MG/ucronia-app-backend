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
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntityMapper;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.UpdateGuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.UpdateGuestEntityMapper;
import com.bernardomg.association.guest.adapter.inbound.jpa.specification.GuestSpecifications;
import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
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
    private static final Logger                 log = LoggerFactory.getLogger(JpaGuestRepository.class);

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final ProfileSpringRepository       profileSpringRepository;

    private final QueryGuestSpringRepository    queryGuestSpringRepository;

    private final UpdateGuestSpringRepository   updateGuestSpringRepository;

    public JpaGuestRepository(final QueryGuestSpringRepository queryGuestSpringRepo,
            final UpdateGuestSpringRepository updateGuestSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo,
            final ProfileSpringRepository profileSpringRepo) {
        super();

        queryGuestSpringRepository = Objects.requireNonNull(queryGuestSpringRepo);
        updateGuestSpringRepository = Objects.requireNonNull(updateGuestSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
        // TODO: remove profile repository
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting guest {}", number);

        // TODO: delete on cascade from the profile
        queryGuestSpringRepository.deleteByNumber(number);
        profileSpringRepository.deleteByNumber(number);

        log.debug("Deleted guest {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if guest {} exists", number);

        exists = queryGuestSpringRepository.existsByNumber(number);

        log.debug("Guest {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Guest> findAll(final GuestFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Guest> read;
        final Pageable                                    pageable;
        final Optional<Specification<QueryGuestEntity>>   spec;

        log.debug("Finding all the guests with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = GuestSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = queryGuestSpringRepository.findAll(pageable)
                .map(QueryGuestEntityMapper::toDomain);
        } else {
            read = queryGuestSpringRepository.findAll(spec.get(), pageable)
                .map(QueryGuestEntityMapper::toDomain);
        }

        log.debug("Found all the guests with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Guest> findOne(final Long number) {
        final Optional<Guest> guest;

        log.trace("Finding guest with number {}", number);

        guest = queryGuestSpringRepository.findByNumber(number)
            .map(QueryGuestEntityMapper::toDomain);

        log.trace("Found guest with number {}: {}", number, guest);

        return guest;
    }

    @Override
    public final Guest save(final Guest guest) {
        final Optional<UpdateGuestEntity> existing;
        final UpdateGuestEntity           entity;
        final Guest                       created;
        final List<Long>                  contactMethodNumbers;
        final List<ContactMethodEntity>   contactMethods;
        final Long                        number;

        log.debug("Saving guest {}", guest);

        existing = updateGuestSpringRepository.findByNumber(guest.number());
        if (existing.isPresent()) {
            entity = UpdateGuestEntityMapper.toEntity(existing.get(), guest);
        } else {
            contactMethodNumbers = guest.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = UpdateGuestEntityMapper.toEntity(guest, contactMethods);
            number = queryGuestSpringRepository.findNextNumber();
            entity.getProfile()
                .setNumber(number);
        }

        setType(entity.getProfile());

        created = UpdateGuestEntityMapper.toDomain(updateGuestSpringRepository.save(entity));

        log.debug("Saved guest {}", created);

        return created;
    }

    @Override
    public final Guest save(final Guest guest, final long number) {
        final UpdateGuestEntity         entity;
        final Guest                     created;
        final Optional<ProfileEntity>   profile;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving guest {} with number {}", guest, number);

        contactMethodNumbers = guest.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = UpdateGuestEntityMapper.toEntity(guest, contactMethods);

        profile = profileSpringRepository.findByNumber(number);
        if (profile.isPresent()) {
            entity.setProfile(profile.get());
        }

        setType(entity.getProfile());

        created = UpdateGuestEntityMapper.toDomain(updateGuestSpringRepository.save(entity));

        log.debug("Saved guest {} with number {}", created, number);

        return created;
    }

    @Override
    public final Collection<Guest> saveAll(final Collection<Guest> guests) {
        final List<UpdateGuestEntity> entities;
        final List<Guest>             saved;
        final AtomicLong              number;

        log.debug("Saving guests {}", guests);

        number = new AtomicLong(queryGuestSpringRepository.findNextNumber());
        entities = guests.stream()
            .map(m -> convert(m, number))
            .toList();

        entities.stream()
            .forEach(m -> setType(m.getProfile()));

        saved = updateGuestSpringRepository.saveAll(entities)
            .stream()
            .map(UpdateGuestEntityMapper::toDomain)
            .toList();

        log.debug("Saved guests {}", saved);

        return saved;
    }

    private final UpdateGuestEntity convert(final Guest guest, final AtomicLong number) {
        final Optional<UpdateGuestEntity> existing;
        final UpdateGuestEntity           entity;
        final List<Long>                  contactMethodNumbers;
        final List<ContactMethodEntity>   contactMethods;

        existing = updateGuestSpringRepository.findByNumber(guest.number());
        if (existing.isPresent()) {
            entity = UpdateGuestEntityMapper.toEntity(existing.get(), guest);
        } else {
            contactMethodNumbers = guest.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = UpdateGuestEntityMapper.toEntity(guest, contactMethods);
            entity.getProfile()
                .setNumber(number.getAndIncrement());
        }

        return entity;
    }

    private final void setType(final ProfileEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(Set.of(GuestEntityConstants.PROFILE_TYPE));
        } else {
            entity.getTypes()
                .add(GuestEntityConstants.PROFILE_TYPE);
        }
    }

}
