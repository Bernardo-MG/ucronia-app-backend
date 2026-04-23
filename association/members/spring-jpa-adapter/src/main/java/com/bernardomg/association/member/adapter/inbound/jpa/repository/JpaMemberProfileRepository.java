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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactMethodEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberFeeTypeEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.ReadMemberProfileSpecifications;
import com.bernardomg.association.member.domain.filter.MemberProfileFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.domain.Sorting.Property;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaMemberProfileRepository implements MemberProfileRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                       log = LoggerFactory.getLogger(JpaMemberProfileRepository.class);

    private final MemberContactMethodSpringRepository memberContactMethodSpringRepository;

    private final MemberFeeTypeSpringRepository       memberFeeTypeSpringRepository;

    private final MemberProfileSpringRepository       memberProfileSpringRepository;

    private final ReadMemberProfileSpringRepository   readMemberProfileSpringRepository;

    public JpaMemberProfileRepository(final ReadMemberProfileSpringRepository readMemberProfileSpringRepo,
            final MemberProfileSpringRepository memberProfileSpringRepo,
            final MemberContactMethodSpringRepository memberContactMethodSpringRepo,
            final MemberFeeTypeSpringRepository memberFeeTypeSpringRepo) {
        super();

        readMemberProfileSpringRepository = Objects.requireNonNull(readMemberProfileSpringRepo);
        memberProfileSpringRepository = Objects.requireNonNull(memberProfileSpringRepo);
        memberContactMethodSpringRepository = Objects.requireNonNull(memberContactMethodSpringRepo);
        memberFeeTypeSpringRepository = Objects.requireNonNull(memberFeeTypeSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member profile {}", number);

        // TODO: delete on cascade from the profile
        readMemberProfileSpringRepository.deleteByNumber(number);

        log.debug("Deleted member profile {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if member profile {} exists", number);

        exists = readMemberProfileSpringRepository.existsByNumber(number);

        log.debug("Member profile {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifier(final String identifier) {
        final boolean exists;

        log.debug("Checking if member profile identifier {} exists", identifier);

        exists = readMemberProfileSpringRepository.existsByIdentifier(identifier);

        log.debug("Member profile identifier {} exists: {}", identifier, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifierForAnother(final long number, final String identifier) {
        final boolean exists;

        log.debug("Checking if identifier {} exists for a member profile distinct from {}", identifier, number);

        exists = readMemberProfileSpringRepository.existsByIdentifierForAnother(number, identifier);

        log.debug("Identifier {} exists for a member profile distinct from {}: {}", identifier, number, exists);

        return exists;
    }

    @Override
    public final Page<MemberProfile> findAll(final MemberProfileFilter filter, final Pagination pagination,
            final Sorting sorting) {
        final org.springframework.data.domain.Page<MemberProfile> read;
        final Pageable                                            pageable;
        final Optional<Specification<ReadMemberProfileEntity>>    spec;
        final Sorting                                             fixedSorting;

        log.debug("Finding all the member profiles with filter {}, pagination {} and sorting {}", filter, pagination,
            sorting);

        fixedSorting = fixSorting(sorting);
        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        spec = ReadMemberProfileSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = readMemberProfileSpringRepository.findAll(pageable)
                .map(MemberProfileEntityMapper::toDomain);
        } else {
            read = readMemberProfileSpringRepository.findAll(spec.get(), pageable)
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

        members = readMemberProfileSpringRepository.findAllByRenewTrue()
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

        members = readMemberProfileSpringRepository.findAllWithRenewalMismatch()
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

        memberProfile = readMemberProfileSpringRepository.findByNumber(number)
            .map(MemberProfileEntityMapper::toDomain);

        log.trace("Found member profile with number {}: {}", number, memberProfile);

        return memberProfile;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = readMemberProfileSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

    @Override
    public final MemberProfile save(final MemberProfile memberProfile) {
        final MemberProfileEntity entity;
        final MemberProfile       created;
        final Long                number;

        log.debug("Saving member profile {}", memberProfile);

        number = readMemberProfileSpringRepository.findNextNumber();
        entity = toEntity(memberProfile, number);

        created = MemberProfileEntityMapper.toDomain(memberProfileSpringRepository.save(entity));

        log.debug("Saved member profile {}", created);

        return created;
    }

    @Override
    public final Collection<MemberProfile> saveAll(final Collection<MemberProfile> memberProfiles) {
        final List<MemberProfileEntity> entities;
        final List<MemberProfile>       saved;
        final AtomicLong                number;

        log.debug("Saving member profiles {}", memberProfiles);

        number = new AtomicLong(readMemberProfileSpringRepository.findNextNumber());
        entities = memberProfiles.stream()
            .map(m -> toEntity(m, number.getAndIncrement()))
            .toList();

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
            // Fix name
            .map(prop -> {
                if (prop.name()
                    .startsWith("name.")) {
                    return new Property(prop.name()
                        .replaceFirst("name\\.", ""), prop.direction());
                }
                return prop;
            })
            .toList();

        return new Sorting(properties);
    }

    private final Collection<MemberContactMethodEntity> getContactMethods(final MemberProfile memberProfile) {
        final Collection<Long> contactMethodNumbers;

        contactMethodNumbers = memberProfile.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        return memberContactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
    }

    private final void setType(final MemberProfileEntity entity) {
        if (entity.getProfile()
            .getTypes() == null) {
            entity.getProfile()
                .setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));
        } else {
            entity.getProfile()
                .getTypes()
                .add(MemberEntityConstants.PROFILE_TYPE);
        }
    }

    private final MemberProfileEntity toEntity(final MemberProfile memberProfile, final Long number) {
        final Optional<MemberProfileEntity>         existing;
        final MemberProfileEntity                   entity;
        final Collection<MemberContactMethodEntity> contactMethods;
        final Optional<MemberFeeTypeEntity>         feeType;
        final Optional<ReadMemberProfileEntity>     profile;

        existing = memberProfileSpringRepository.findByNumber(memberProfile.number());
        contactMethods = getContactMethods(memberProfile);
        if (existing.isPresent()) {
            entity = MemberProfileEntityMapper.toEntity(existing.get(), memberProfile, contactMethods);
        } else {
            entity = MemberProfileEntityMapper.toEntity(memberProfile, contactMethods);

            profile = readMemberProfileSpringRepository.findByNumber(memberProfile.number());
            if (profile.isPresent()) {
                entity.getProfile()
                    .setNumber(profile.get()
                        .getNumber());
            } else {
                entity.getProfile()
                    .setNumber(number);
            }
        }

        feeType = memberFeeTypeSpringRepository.findByNumber(memberProfile.feeType()
            .number());
        entity.setFeeType(feeType.orElse(null));

        setType(entity);

        return entity;
    }

}
