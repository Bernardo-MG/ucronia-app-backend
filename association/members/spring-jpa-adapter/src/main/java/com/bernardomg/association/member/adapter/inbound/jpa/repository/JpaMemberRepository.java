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
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactMethodEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberFeeTypeEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberInnerProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.ReadMemberSpecifications;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.ContactChannel;
import com.bernardomg.association.member.domain.model.Member.ContactMethod;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.domain.Sorting.Property;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaMemberRepository implements MemberRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                       log = LoggerFactory.getLogger(JpaMemberRepository.class);

    private final MemberContactMethodSpringRepository memberContactMethodSpringRepository;

    private final MemberFeeTypeSpringRepository       memberFeeTypeSpringRepository;

    private final MemberSpringRepository              memberSpringRepository;

    private final ReadMemberSpringRepository          readMemberSpringRepository;

    private final MemberInnerProfileSpringRepository          memberInnerProfileSpringRepository;

    public JpaMemberRepository(final ReadMemberSpringRepository readMemberSpringRepo,
            final MemberSpringRepository memberSpringRepo,
            final MemberContactMethodSpringRepository memberContactMethodSpringRepo,
            final MemberFeeTypeSpringRepository memberFeeTypeSpringRepo,
            final MemberInnerProfileSpringRepository          memberInnerProfileSpringRepo) {
        super();

        readMemberSpringRepository = Objects.requireNonNull(readMemberSpringRepo);
        memberSpringRepository = Objects.requireNonNull(memberSpringRepo);
        memberContactMethodSpringRepository = Objects.requireNonNull(memberContactMethodSpringRepo);
        memberFeeTypeSpringRepository = Objects.requireNonNull(memberFeeTypeSpringRepo);
        memberInnerProfileSpringRepository = Objects.requireNonNull(memberInnerProfileSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member profile {}", number);

        readMemberSpringRepository.deleteByNumber(number);

        log.debug("Deleted member profile {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if member profile {} exists", number);

        exists = readMemberSpringRepository.existsByNumber(number);

        log.debug("Member profile {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifier(final String identifier) {
        final boolean exists;

        log.debug("Checking if member profile identifier {} exists", identifier);

        exists = readMemberSpringRepository.existsByIdentifier(identifier);

        log.debug("Member profile identifier {} exists: {}", identifier, exists);

        return exists;
    }

    @Override
    public final boolean existsByIdentifierForAnother(final long number, final String identifier) {
        final boolean exists;

        log.debug("Checking if identifier {} exists for a member profile distinct from {}", identifier, number);

        exists = readMemberSpringRepository.existsByIdentifierForAnother(number, identifier);

        log.debug("Identifier {} exists for a member profile distinct from {}: {}", identifier, number, exists);

        return exists;
    }

    @Override
    public final Page<Member> findAll(final MemberFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Member> read;
        final Pageable                                     pageable;
        final Optional<Specification<ReadMemberEntity>>    spec;
        final Sorting                                      fixedSorting;

        log.debug("Finding all the member profiles with filter {}, pagination {} and sorting {}", filter, pagination,
            sorting);

        fixedSorting = fixSorting(sorting);
        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        spec = ReadMemberSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = readMemberSpringRepository.findAll(pageable)
                .map(MemberEntityMapper::toDomain);
        } else {
            read = readMemberSpringRepository.findAll(spec.get(), pageable)
                .map(MemberEntityMapper::toDomain);
        }

        log.debug("Found all the member profiles with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            fixedSorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<Member> findAllToRenew() {
        final Collection<Member> members;

        log.debug("Finding all the members to renew");

        members = readMemberSpringRepository.findAllByRenewTrue()
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        log.debug("Found all the members to renew: {}", members);

        return members;
    }

    @Override
    public final Collection<Member> findAllWithRenewalMismatch() {
        final Collection<Member> members;

        log.debug("Finding all the members with a renewal mismatch");

        members = readMemberSpringRepository.findAllWithRenewalMismatch()
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        log.debug("Found all the members with a renewal mismatch: {}", members);

        return members;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.trace("Finding member profile with number {}", number);

        member = readMemberSpringRepository.findByNumber(number)
            .map(MemberEntityMapper::toDomain);

        log.trace("Found member profile with number {}: {}", number, member);

        return member;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = readMemberSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

    @Override
    public final Member save(final Member member) {
        final MemberEntity   entity;
        final Member         created;
        final Supplier<Long> number;

        log.debug("Saving member profile {}", member);

        number = () -> readMemberSpringRepository.findNextNumber();
        entity = toEntity(member, number);

        created = MemberEntityMapper.toDomain(memberSpringRepository.save(entity));

        log.debug("Saved member profile {}", created);

        return created;
    }

    @Override
    public final Collection<Member> saveAll(final Collection<Member> members) {
        final List<MemberEntity> entities;
        final List<Member>       saved;
        final Supplier<Long>     number;
        final AtomicLong         sourceNumber;

        log.debug("Saving member profiles {}", members);

        sourceNumber = new AtomicLong(readMemberSpringRepository.findNextNumber());
        number = () -> sourceNumber.getAndIncrement();
        entities = members.stream()
            .map(m -> toEntity(m, number))
            .toList();

        saved = memberSpringRepository.saveAll(entities)
            .stream()
            .map(MemberEntityMapper::toDomain)
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

    private final Collection<MemberContactMethodEntity> getContactMethods(final Member member) {
        final Collection<Long> contactMethodNumbers;

        contactMethodNumbers = member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        return memberContactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
    }

    private final void setType(final MemberEntity entity) {
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

    private final MemberEntity toEntity(final Member member, final Supplier<Long> number) {
        final Optional<MemberEntity>                existing;
        final MemberEntity                          entity;
        final Collection<MemberContactMethodEntity> contactMethods;
        final Optional<MemberFeeTypeEntity>         feeType;
        final Optional<MemberInnerProfileEntity>            profile;

        existing = memberSpringRepository.findByNumber(member.number());
        contactMethods = getContactMethods(member);
        if (existing.isPresent()) {
            entity = MemberEntityMapper.toEntity(existing.get(), member, contactMethods);
        } else {
            entity = MemberEntityMapper.toEntity(member, contactMethods);

            profile = memberInnerProfileSpringRepository.findByNumber(member.number());
            if (profile.isPresent()) {
                entity.setProfile(profile.get());
            } else {
                entity.getProfile()
                    .setNumber(number.get());
            }
        }

        feeType = memberFeeTypeSpringRepository.findByNumber(member.feeType()
            .number());
        entity.setFeeType(feeType.orElse(null));

        setType(entity);

        return entity;
    }

}
