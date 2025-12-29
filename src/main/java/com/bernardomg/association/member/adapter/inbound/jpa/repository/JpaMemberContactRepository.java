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

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberProfileEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.model.UpdateMemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.UpdateMemberProfileEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MemberContactSpecifications;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
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
public final class JpaMemberContactRepository implements MemberContactRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                       log = LoggerFactory.getLogger(JpaMemberContactRepository.class);

    private final ContactMethodSpringRepository       contactMethodSpringRepository;

    private final ProfileSpringRepository             profileSpringRepository;

    private final QueryMemberContactSpringRepository  queryMemberContactSpringRepository;

    private final UpdateMemberContactSpringRepository updateMemberContactSpringRepository;

    public JpaMemberContactRepository(final QueryMemberContactSpringRepository queryMemberContactSpringRepo,
            final UpdateMemberContactSpringRepository updateMemberContactSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo,
            final ProfileSpringRepository profileSpringRepo) {
        super();

        queryMemberContactSpringRepository = Objects.requireNonNull(queryMemberContactSpringRepo);
        updateMemberContactSpringRepository = Objects.requireNonNull(updateMemberContactSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
        // TODO: remove profile repository
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting memberContact {}", number);

        // TODO: delete on cascade from the profile
        queryMemberContactSpringRepository.deleteByNumber(number);
        profileSpringRepository.deleteByNumber(number);

        log.debug("Deleted memberContact {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if memberContact {} exists", number);

        exists = queryMemberContactSpringRepository.existsByNumber(number);

        log.debug("MemberContact {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<MemberProfile> findAll(final MemberFilter filter, final Pagination pagination,
            final Sorting sorting) {
        final org.springframework.data.domain.Page<MemberProfile> read;
        final Pageable                                            pageable;
        final Optional<Specification<QueryMemberProfileEntity>>   spec;

        log.debug("Finding all the memberContacts with filter {}, pagination {} and sorting {}", filter, pagination,
            sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = MemberContactSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = queryMemberContactSpringRepository.findAll(pageable)
                .map(QueryMemberProfileEntityMapper::toDomain);
        } else {
            read = queryMemberContactSpringRepository.findAll(spec.get(), pageable)
                .map(QueryMemberProfileEntityMapper::toDomain);
        }

        log.debug("Found all the memberContacts with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            sorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<MemberProfile> findOne(final Long number) {
        final Optional<MemberProfile> memberContact;

        log.trace("Finding memberContact with number {}", number);

        memberContact = queryMemberContactSpringRepository.findByNumber(number)
            .map(QueryMemberProfileEntityMapper::toDomain);

        log.trace("Found memberContact with number {}: {}", number, memberContact);

        return memberContact;
    }

    @Override
    public final MemberProfile save(final MemberProfile memberContact) {
        final Optional<UpdateMemberProfileEntity> existing;
        final UpdateMemberProfileEntity           entity;
        final MemberProfile                       created;
        final List<Long>                          contactMethodNumbers;
        final List<ContactMethodEntity>           contactMethods;
        final Long                                number;

        log.debug("Saving memberContact {}", memberContact);

        existing = updateMemberContactSpringRepository.findByNumber(memberContact.number());
        if (existing.isPresent()) {
            entity = UpdateMemberProfileEntityMapper.toEntity(existing.get(), memberContact);
        } else {
            contactMethodNumbers = memberContact.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = UpdateMemberProfileEntityMapper.toEntity(memberContact, contactMethods);
            number = queryMemberContactSpringRepository.findNextNumber();
            entity.getProfile()
                .setNumber(number);
        }

        setType(entity.getProfile());

        created = UpdateMemberProfileEntityMapper.toDomain(updateMemberContactSpringRepository.save(entity));

        log.debug("Saved memberContact {}", created);

        return created;
    }

    @Override
    public final MemberProfile save(final MemberProfile memberContact, final long number) {
        final UpdateMemberProfileEntity entity;
        final MemberProfile             created;
        final Optional<ProfileEntity>   profile;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving memberContact {} with number {}", memberContact, number);

        contactMethodNumbers = memberContact.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = UpdateMemberProfileEntityMapper.toEntity(memberContact, contactMethods);

        profile = profileSpringRepository.findByNumber(number);
        if (profile.isPresent()) {
            entity.setProfile(profile.get());
        }

        setType(entity.getProfile());

        created = UpdateMemberProfileEntityMapper.toDomain(updateMemberContactSpringRepository.save(entity));

        log.debug("Saved memberContact {} with number {}", created, number);

        return created;
    }

    @Override
    public final Collection<MemberProfile> saveAll(final Collection<MemberProfile> memberContacts) {
        final List<UpdateMemberProfileEntity> entities;
        final List<MemberProfile>             saved;
        final AtomicLong                      number;

        log.debug("Saving memberContacts {}", memberContacts);

        number = new AtomicLong(queryMemberContactSpringRepository.findNextNumber());
        entities = memberContacts.stream()
            .map(m -> convert(m, number))
            .toList();

        entities.stream()
            .forEach(m -> setType(m.getProfile()));

        saved = updateMemberContactSpringRepository.saveAll(entities)
            .stream()
            .map(UpdateMemberProfileEntityMapper::toDomain)
            .toList();

        log.debug("Saved memberContacts {}", saved);

        return saved;
    }

    private final UpdateMemberProfileEntity convert(final MemberProfile memberContact, final AtomicLong number) {
        final Optional<UpdateMemberProfileEntity> existing;
        final UpdateMemberProfileEntity           entity;
        final List<Long>                          contactMethodNumbers;
        final List<ContactMethodEntity>           contactMethods;

        existing = updateMemberContactSpringRepository.findByNumber(memberContact.number());
        if (existing.isPresent()) {
            entity = UpdateMemberProfileEntityMapper.toEntity(existing.get(), memberContact);
        } else {
            contactMethodNumbers = memberContact.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = UpdateMemberProfileEntityMapper.toEntity(memberContact, contactMethods);
            entity.getProfile()
                .setNumber(number.getAndIncrement());
        }

        return entity;
    }

    private final void setType(final ProfileEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(Set.of(MemberEntityConstants.CONTACT_TYPE));
        } else {
            entity.getTypes()
                .add(MemberEntityConstants.CONTACT_TYPE);
        }
    }

}
