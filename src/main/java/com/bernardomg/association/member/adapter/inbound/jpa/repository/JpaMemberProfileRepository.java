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

package com.bernardomg.association.member.adapter.inbound.jpa.repository;

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

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTypeSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MemberProfileSpecifications;
import com.bernardomg.association.member.domain.filter.MemberProfileFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaMemberProfileRepository implements MemberProfileRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log                = LoggerFactory
        .getLogger(JpaMemberProfileRepository.class);

    private static final Collection<String>     PROFILE_PROPERTIES = List.of("firstName", "lastName", "number");

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final FeeTypeSpringRepository       feeTypeSpringRepository;

    private final MemberProfileSpringRepository memberProfileSpringRepository;

    private final ProfileSpringRepository       profileSpringRepository;

    public JpaMemberProfileRepository(final MemberProfileSpringRepository updateMemberProfileSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo,
            final ProfileSpringRepository profileSpringRepo, final FeeTypeSpringRepository feeTypeSpringRepo) {
        super();

        memberProfileSpringRepository = Objects.requireNonNull(updateMemberProfileSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
        feeTypeSpringRepository = Objects.requireNonNull(feeTypeSpringRepo);
        // TODO: remove profile repository
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member profile {}", number);

        // TODO: delete on cascade from the profile
        memberProfileSpringRepository.deleteByNumber(number);
        profileSpringRepository.deleteByNumber(number);

        log.debug("Deleted member profile {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if member profile {} exists", number);

        exists = memberProfileSpringRepository.existsByNumber(number);

        log.debug("Member profile {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<MemberProfile> findAll(final MemberProfileFilter filter, final Pagination pagination,
            final Sorting sorting) {
        final org.springframework.data.domain.Page<MemberProfile> read;
        final Pageable                                            pageable;
        final Optional<Specification<MemberProfileEntity>>        spec;
        final Sorting                                             fixedSorting;

        fixedSorting = fixSorting(sorting);
        log.debug("Finding all the member profiles with filter {}, pagination {} and sorting {}", filter, pagination,
            fixedSorting);

        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        spec = MemberProfileSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = memberProfileSpringRepository.findAll(pageable)
                .map(MemberProfileEntityMapper::toDomain);
        } else {
            read = memberProfileSpringRepository.findAll(spec.get(), pageable)
                .map(MemberProfileEntityMapper::toDomain);
        }

        log.debug("Found all the member profiles with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            fixedSorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<MemberProfile> findAllToRenew() {
        final Collection<MemberProfile> members;

        log.debug("Finding all the members to renew");

        members = memberProfileSpringRepository.findAllByRenewTrue()
            .stream()
            .map(MemberProfileEntityMapper::toDomain)
            .toList();

        log.debug("Found all the members to renew: {}", members);

        return members;
    }

    @Override
    public final Collection<MemberProfile> findAllWithRenewalMismatch() {
        final Collection<MemberProfile> members;

        log.debug("Finding all the members with a renewal mismatch");

        members = memberProfileSpringRepository.findAllWithRenewalMismatch()
            .stream()
            .map(MemberProfileEntityMapper::toDomain)
            .toList();

        log.debug("Found all the members with a renewal mismatch: {}", members);

        return members;
    }

    @Override
    public final Optional<MemberProfile> findOne(final Long number) {
        final Optional<MemberProfile> memberProfile;

        log.trace("Finding member profile with number {}", number);

        memberProfile = memberProfileSpringRepository.findByNumber(number)
            .map(MemberProfileEntityMapper::toDomain);

        log.trace("Found member profile with number {}: {}", number, memberProfile);

        return memberProfile;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = memberProfileSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

    @Override
    public final MemberProfile save(final MemberProfile memberProfile) {
        final Optional<MemberProfileEntity> existing;
        final MemberProfileEntity           entity;
        final MemberProfile                 created;
        final List<Long>                    contactMethodNumbers;
        final List<ContactMethodEntity>     contactMethods;
        final Long                          number;
        final Optional<FeeTypeEntity>       feeType;

        log.debug("Saving member profile {}", memberProfile);

        existing = memberProfileSpringRepository.findByNumber(memberProfile.number());
        if (existing.isPresent()) {
            entity = MemberProfileEntityMapper.toEntity(existing.get(), memberProfile);
        } else {
            contactMethodNumbers = memberProfile.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = MemberProfileEntityMapper.toEntity(memberProfile, contactMethods);
            number = memberProfileSpringRepository.findNextNumber();
            entity.getProfile()
                .setNumber(number);
        }

        // TODO: verify it exists
        feeType = feeTypeSpringRepository.findByNumber(memberProfile.feeType()
            .number());
        if (feeType.isEmpty()) {
            log.warn("Missing fee type {}", memberProfile.feeType()
                .number());
        }
        entity.setFeeType(feeType.orElse(null));

        setType(entity.getProfile());

        created = MemberProfileEntityMapper.toDomain(memberProfileSpringRepository.save(entity));

        log.debug("Saved member profile {}", created);

        return created;
    }

    @Override
    public final MemberProfile save(final MemberProfile memberProfile, final long number) {
        final MemberProfileEntity       entity;
        final MemberProfile             created;
        final Optional<ProfileEntity>   profile;
        final Optional<FeeTypeEntity>   feeType;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving member profile {} with number {}", memberProfile, number);

        contactMethodNumbers = memberProfile.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = MemberProfileEntityMapper.toEntity(memberProfile, contactMethods);

        profile = profileSpringRepository.findByNumber(number);
        if (profile.isPresent()) {
            entity.setProfile(profile.get());
        }

        feeType = feeTypeSpringRepository.findByNumber(memberProfile.number());
        if (feeType.isEmpty()) {
            log.warn("Missing fee type {}", memberProfile.feeType()
                .number());
        }
        entity.setFeeType(feeType.orElse(null));

        setType(entity.getProfile());

        created = MemberProfileEntityMapper.toDomain(memberProfileSpringRepository.save(entity));

        log.debug("Saved member profile {} with number {}", created, number);

        return created;
    }

    @Override
    public final Collection<MemberProfile> saveAll(final Collection<MemberProfile> memberProfiles) {
        final List<MemberProfileEntity> entities;
        final List<MemberProfile>       saved;
        final AtomicLong                number;

        log.debug("Saving member profiles {}", memberProfiles);

        number = new AtomicLong(memberProfileSpringRepository.findNextNumber());
        entities = memberProfiles.stream()
            .map(m -> toEntity(m, number))
            .toList();

        entities.stream()
            .forEach(m -> setType(m.getProfile()));

        saved = memberProfileSpringRepository.saveAll(entities)
            .stream()
            .map(MemberProfileEntityMapper::toDomain)
            .toList();

        log.debug("Saved member profiles {}", saved);

        return saved;
    }

    private final Sorting fixSorting(final Sorting sorting) {
        final Collection<Property> properties;

        properties = sorting.properties()
            .stream()
            .map(prop -> {
                if (PROFILE_PROPERTIES.contains(prop.name())) {
                    return new Property("profile." + prop.name(), prop.direction());
                }
                return prop;
            })
            .toList();

        return new Sorting(properties);
    }

    private final void setType(final ProfileEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));
        } else {
            entity.getTypes()
                .add(MemberEntityConstants.PROFILE_TYPE);
        }
    }

    private final MemberProfileEntity toEntity(final MemberProfile memberProfile, final AtomicLong number) {
        final Optional<MemberProfileEntity> existing;
        final MemberProfileEntity           entity;
        final List<Long>                    contactMethodNumbers;
        final List<ContactMethodEntity>     contactMethods;
        final Optional<FeeTypeEntity>       feeType;

        // TODO: move to repository
        existing = memberProfileSpringRepository.findByNumber(memberProfile.number());
        if (existing.isPresent()) {
            entity = MemberProfileEntityMapper.toEntity(existing.get(), memberProfile);
        } else {
            contactMethodNumbers = memberProfile.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = MemberProfileEntityMapper.toEntity(memberProfile, contactMethods);
            entity.getProfile()
                .setNumber(number.getAndIncrement());
        }
        // TODO: verify it exists
        feeType = feeTypeSpringRepository.findByNumber(memberProfile.number());
        if (feeType.isEmpty()) {
            log.warn("Missing fee type {}", memberProfile.feeType()
                .number());
        }
        entity.setFeeType(feeType.orElse(null));

        return entity;
    }

}
