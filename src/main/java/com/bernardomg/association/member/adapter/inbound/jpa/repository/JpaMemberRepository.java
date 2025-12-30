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
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.model.UpdateMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.UpdateMemberEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MemberSpecifications;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaMemberRepository implements MemberRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                log = LoggerFactory.getLogger(JpaMemberRepository.class);

    private final ProfileSpringRepository      profileSpringRepository;

    private final QueryMemberSpringRepository  queryMemberSpringRepository;

    private final UpdateMemberSpringRepository updateMemberSpringRepository;

    public JpaMemberRepository(final QueryMemberSpringRepository queryMemberSpringRepo,
            final UpdateMemberSpringRepository updateMemberSpringRepo,
            final ProfileSpringRepository profileSpringRepo) {
        super();

        queryMemberSpringRepository = Objects.requireNonNull(queryMemberSpringRepo);
        updateMemberSpringRepository = Objects.requireNonNull(updateMemberSpringRepo);
        // TODO: remove profile repository
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member {}", number);

        // TODO: delete on cascade from the profile
        queryMemberSpringRepository.deleteByNumber(number);
        profileSpringRepository.deleteByNumber(number);

        log.debug("Deleted member {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if member {} exists", number);

        exists = queryMemberSpringRepository.existsByNumber(number);

        log.debug("Member {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Member> findAll(final MemberFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Member> read;
        final Pageable                                     pageable;
        final Optional<Specification<QueryMemberEntity>>   spec;

        log.debug("Finding all the members with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = MemberSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = queryMemberSpringRepository.findAll(pageable)
                .map(QueryMemberEntityMapper::toDomain);
        } else {
            read = queryMemberSpringRepository.findAll(spec.get(), pageable)
                .map(QueryMemberEntityMapper::toDomain);
        }

        log.debug("Found all the members with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<Member> findAllToRenew() {
        final Collection<Member> members;

        log.debug("Finding all the members to renew");

        members = queryMemberSpringRepository.findAllByRenewTrue()
            .stream()
            .map(QueryMemberEntityMapper::toDomain)
            .toList();

        log.debug("Found all the members to renew: {}", members);

        return members;
    }

    @Override
    public final Collection<Member> findAllWithRenewalMismatch() {
        final Collection<Member> members;

        log.debug("Finding all the members with a renewal mismatch");

        members = queryMemberSpringRepository.findAllWithRenewalMismatch()
            .stream()
            .map(QueryMemberEntityMapper::toDomain)
            .toList();

        log.debug("Found all the members with a renewal mismatch: {}", members);

        return members;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.trace("Finding member with number {}", number);

        member = queryMemberSpringRepository.findByNumber(number)
            .map(QueryMemberEntityMapper::toDomain);

        log.trace("Found member with number {}: {}", number, member);

        return member;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = queryMemberSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

    @Override
    public final Member save(final Member member) {
        final Optional<UpdateMemberEntity> existing;
        final UpdateMemberEntity           entity;
        final Member                       created;
        final Long                         number;

        log.debug("Saving member {}", member);

        existing = updateMemberSpringRepository.findByNumber(member.number());
        if (existing.isPresent()) {
            entity = UpdateMemberEntityMapper.toEntity(existing.get(), member);
        } else {
            entity = UpdateMemberEntityMapper.toEntity(member);
            number = queryMemberSpringRepository.findNextNumber();
            entity.getProfile()
                .setNumber(number);
        }

        setType(entity.getProfile());

        created = UpdateMemberEntityMapper.toDomain(updateMemberSpringRepository.save(entity));

        log.debug("Saved member {}", created);

        return created;
    }

    @Override
    public final Member save(final Member member, final long number) {
        final UpdateMemberEntity      entity;
        final Member                  created;
        final Optional<ProfileEntity> profile;

        log.debug("Saving member {} with number {}", member, number);

        entity = UpdateMemberEntityMapper.toEntity(member);

        profile = profileSpringRepository.findByNumber(number);
        if (profile.isPresent()) {
            entity.setProfile(profile.get());
        }

        setType(entity.getProfile());

        created = UpdateMemberEntityMapper.toDomain(updateMemberSpringRepository.save(entity));

        log.debug("Saved member {} with number {}", created, number);

        return created;
    }

    @Override
    public final Collection<Member> saveAll(final Collection<Member> members) {
        final List<UpdateMemberEntity> entities;
        final List<Member>             saved;
        final AtomicLong               number;

        log.debug("Saving members {}", members);

        number = new AtomicLong(queryMemberSpringRepository.findNextNumber());
        entities = members.stream()
            .map(m -> convert(m, number))
            .toList();

        entities.stream()
            .forEach(m -> setType(m.getProfile()));

        saved = updateMemberSpringRepository.saveAll(entities)
            .stream()
            .map(UpdateMemberEntityMapper::toDomain)
            .toList();

        log.debug("Saved members {}", saved);

        return saved;
    }

    private final UpdateMemberEntity convert(final Member member, final AtomicLong number) {
        final Optional<UpdateMemberEntity> existing;
        final UpdateMemberEntity           entity;

        existing = updateMemberSpringRepository.findByNumber(member.number());
        if (existing.isPresent()) {
            entity = UpdateMemberEntityMapper.toEntity(existing.get(), member);
        } else {
            entity = UpdateMemberEntityMapper.toEntity(member);
            entity.getProfile()
                .setNumber(number.getAndIncrement());
        }

        return entity;
    }

    private final void setType(final ProfileEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));
        } else {
            entity.setTypes(new HashSet<>(entity.getTypes()));
            entity.getTypes()
                .add(MemberEntityConstants.PROFILE_TYPE);
        }
    }

}
