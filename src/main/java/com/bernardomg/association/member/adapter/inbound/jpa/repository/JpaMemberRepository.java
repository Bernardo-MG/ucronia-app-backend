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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MemberSpecifications;
import com.bernardomg.association.member.domain.filter.MemberQuery;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.MemberRepository;
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
    private static final Logger                 log = LoggerFactory.getLogger(JpaMemberRepository.class);

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final MemberSpringRepository        memberSpringRepository;

    public JpaMemberRepository(final MemberSpringRepository memberSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo) {
        super();

        memberSpringRepository = Objects.requireNonNull(memberSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
    }

    @Override
    public final Page<Member> findAll(final MemberQuery filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Member> read;
        final Pageable                                     pageable;
        final Optional<Specification<MemberEntity>>        spec;

        log.debug("Finding all the members with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = MemberSpecifications.filter(filter);
        if (spec.isEmpty()) {
            read = memberSpringRepository.findAll(pageable)
                .map(MemberEntityMapper::toDomain);
        } else {
            read = memberSpringRepository.findAll(spec.get(), pageable)
                .map(MemberEntityMapper::toDomain);
        }

        log.debug("Found all the members with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Page<PublicMember> findAllPublic(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<PublicMember> read;
        final Pageable                                           pageable;

        log.trace("Finding all the public members with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = memberSpringRepository.findAllActive(pageable)
            .map(MemberEntityMapper::toPublicDomain);

        log.trace("Found all the public members with pagination {} and sorting {}: {}", pagination, sorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Collection<Member> findAllToRenew() {
        final Collection<Member> members;

        log.debug("Finding all the members to renew");

        members = memberSpringRepository.findAllByRenewMembershipTrue()
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

        members = memberSpringRepository.findAllWithRenewalMismatch()
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        log.debug("Found all the members with a renewal mismatch: {}", members);

        return members;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.trace("Finding member with number {}", number);

        member = memberSpringRepository.findByNumber(number)
            .map(MemberEntityMapper::toDomain);

        log.trace("Found member with number {}: {}", number, member);

        return member;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = memberSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

    @Override
    public final Member save(final Member member) {
        final Optional<MemberEntity>    existing;
        final MemberEntity              entity;
        final Member                    created;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving member {}", member);

        contactMethodNumbers = member.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        // TODO: exception for missing contact methods
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = MemberEntityMapper.toEntity(member, contactMethods);

        existing = memberSpringRepository.findByNumber(member.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = MemberEntityMapper.toDomain(memberSpringRepository.save(entity));

        log.debug("Saved member {}", created);

        return created;
    }

    @Override
    public final Collection<Member> saveAll(final Collection<Member> members) {
        final List<MemberEntity>        entities;
        final List<Member>              saved;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving members {}", members);

        contactMethodNumbers = members.stream()
            .map(Member::contactChannels)
            .flatMap(Collection::stream)
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        // TODO: exception for missing contact methods
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entities = members.stream()
            .map(m -> MemberEntityMapper.toEntity(m, contactMethods))
            .toList();

        saved = memberSpringRepository.saveAll(entities)
            .stream()
            .map(MemberEntityMapper::toDomain)
            .toList();

        log.debug("Saved members {}", saved);

        return saved;
    }

}
