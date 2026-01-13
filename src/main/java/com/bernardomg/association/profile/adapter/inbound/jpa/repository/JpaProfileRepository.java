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

package com.bernardomg.association.profile.adapter.inbound.jpa.repository;

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

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntityMapper;
import com.bernardomg.association.profile.adapter.inbound.jpa.specification.ProfileSpecifications;
import com.bernardomg.association.profile.domain.filter.ProfileQuery;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaProfileRepository implements ProfileRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaProfileRepository.class);

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final ProfileSpringRepository       profileSpringRepository;

    public JpaProfileRepository(final ProfileSpringRepository profileSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo) {
        super();

        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting profile {}", number);

        profileSpringRepository.deleteByNumber(number);

        log.debug("Deleted profile {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if profile {} exists", number);

        exists = profileSpringRepository.existsByNumber(number);

        log.debug("Profile {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifier(final String identifier) {
        final boolean exists;

        log.debug("Checking if profile identifier {} exists", identifier);

        exists = profileSpringRepository.existsByIdentifier(identifier);

        log.debug("Profile identifier {} exists: {}", identifier, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifierForAnother(final long number, final String identifier) {
        final boolean exists;

        log.debug("Checking if identifier {} exists for a profile distinct from {}", identifier, number);

        exists = profileSpringRepository.existsByIdentifierForAnother(number, identifier);

        log.debug("Identifier {} exists for a profile distinct from {}: {}", identifier, number, exists);

        return exists;
    }

    @Override
    public final Page<Profile> findAll(final ProfileQuery filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Profile> read;
        final Pageable                                      pageable;
        final Optional<Specification<ProfileEntity>>        spec;

        log.debug("Finding all the profiles with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = ProfileSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = profileSpringRepository.findAll(pageable)
                .map(ProfileEntityMapper::toDomain);
        } else {
            read = profileSpringRepository.findAll(spec.get(), pageable)
                .map(ProfileEntityMapper::toDomain);
        }

        log.debug("Found all the profiles with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            sorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Profile> findOne(final Long number) {
        final Optional<Profile> profile;

        log.debug("Finding profile with number {}", number);

        profile = profileSpringRepository.findByNumber(number)
            .map(ProfileEntityMapper::toDomain);

        log.debug("Found profile with number {}: {}", number, profile);

        return profile;
    }

    @Override
    public final Profile save(final Profile profile) {
        final Optional<ProfileEntity>   existing;
        final ProfileEntity             entity;
        final Profile                   created;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;
        final Long                      number;

        log.debug("Saving profile {}", profile);

        contactMethodNumbers = profile.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = ProfileEntityMapper.toEntity(profile, contactMethods);

        existing = profileSpringRepository.findByNumber(profile.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
            // Can't change types manually
            entity.setTypes(existing.get()
                .getTypes());
        } else {
            number = profileSpringRepository.findNextNumber();
            entity.setNumber(number);
        }

        created = ProfileEntityMapper.toDomain(profileSpringRepository.save(entity));

        log.debug("Saved profile {}", created);

        return created;
    }

    @Override
    public final Collection<Profile> saveAll(final Collection<Profile> profiles) {
        final List<ProfileEntity>       entities;
        final List<Profile>             saved;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;
        final AtomicLong                number;

        log.debug("Saving profiles {}", profiles);

        contactMethodNumbers = profiles.stream()
            .map(Profile::contactChannels)
            .flatMap(Collection::stream)
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);

        number = new AtomicLong(profileSpringRepository.findNextNumber());
        entities = profiles.stream()
            .map(m -> convert(m, contactMethods, number))
            .toList();

        saved = profileSpringRepository.saveAll(entities)
            .stream()
            .map(ProfileEntityMapper::toDomain)
            .toList();

        log.debug("Saved profiles {}", saved);

        return saved;
    }

    private final ProfileEntity convert(final Profile profile, final List<ContactMethodEntity> contactMethods,
            final AtomicLong number) {
        final Optional<ProfileEntity> existing;
        final ProfileEntity           entity;

        existing = profileSpringRepository.findByNumber(profile.number());
        if (existing.isPresent()) {
            entity = ProfileEntityMapper.toEntity(profile, contactMethods, existing.get());
        } else {
            entity = ProfileEntityMapper.toEntity(profile, contactMethods);
            entity.setNumber(number.getAndIncrement());
        }

        return entity;
    }

}
