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

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntityMapper;
import com.bernardomg.association.member.domain.model.Member;
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
    private static final Logger               log = LoggerFactory.getLogger(JpaMemberRepository.class);

    private final ContactSpringRepository     contactSpringRepository;

    private final MemberSpringRepository      memberSpringRepository;

    private final QueryMemberSpringRepository queryMemberSpringRepository;

    public JpaMemberRepository(final MemberSpringRepository memberSpringRepo,
            final QueryMemberSpringRepository queryMemberSpringRepo, final ContactSpringRepository contactSpringRepo) {
        super();

        memberSpringRepository = Objects.requireNonNull(memberSpringRepo);
        queryMemberSpringRepository = Objects.requireNonNull(queryMemberSpringRepo);
        contactSpringRepository = Objects.requireNonNull(contactSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member {}", number);

        // TODO: delete on cascade from the contact
        memberSpringRepository.deleteByNumber(number);
        contactSpringRepository.deleteByNumber(number);

        log.debug("Deleted member {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if member {} exists", number);

        exists = memberSpringRepository.existsByNumber(number);

        log.debug("Member {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Member> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Member> read;
        final Pageable                                     pageable;

        log.trace("Finding all the members with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        // TODO: use a specific repository for members
        read = queryMemberSpringRepository.findAllActive(pageable)
            .map(QueryMemberEntityMapper::toDomain);

        log.trace("Found all the members with pagination {} and sorting {}: {}", pagination, sorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the members");

        number = contactSpringRepository.findNextNumber();

        log.debug("Found next number for the members: {}", number);

        return number;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.trace("Finding member with number {}", number);

        // TODO: use a specific repository for members
        member = queryMemberSpringRepository.findByNumber(number)
            .map(QueryMemberEntityMapper::toDomain);

        log.trace("Found member with number {}: {}", number, member);

        return member;
    }

    @Override
    public final Member save(final Member member) {
        final Optional<ContactEntity> existingContact;
        final MemberEntity            entity;
        final ContactEntity           contactEntity;
        final Member                  created;
        final ContactEntity           createdContact;

        log.debug("Saving member {}", member);

        entity = MemberEntityMapper.toEntity(member);
        existingContact = contactSpringRepository.findByNumber(member.number());
        if (existingContact.isPresent()) {
            contactEntity = existingContact.get();
            contactEntity.setFirstName(member.name()
                .firstName());
            contactEntity.setLastName(member.name()
                .lastName());
        } else {
            contactEntity = MemberEntityMapper.toContactEntity(member);
        }

        createdContact = contactSpringRepository.save(contactEntity);

        entity.setId(createdContact.getId());
        entity.setContact(createdContact);
        created = MemberEntityMapper.toDomain(memberSpringRepository.save(entity));

        log.debug("Saved member {}", created);

        return created;
    }

}
