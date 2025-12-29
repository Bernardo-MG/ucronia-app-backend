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

package com.bernardomg.association.member.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the member contact service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultMemberContactService implements MemberProfileService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultMemberContactService.class);

    private final MemberContactRepository memberContactRepository;

    public DefaultMemberContactService(final MemberContactRepository memberContactRepo) {
        super();

        memberContactRepository = Objects.requireNonNull(memberContactRepo);
    }

    @Override
    public final MemberProfile create(final MemberProfile memberContact) {
        final MemberProfile toCreate;
        final MemberProfile created;

        log.debug("Creating member contact {}", memberContact);

        toCreate = new MemberProfile(memberContact.identifier(), 0L, memberContact.name(), memberContact.birthDate(),
            memberContact.contactChannels(), memberContact.comments(), memberContact.active(), memberContact.renew(),
            memberContact.types());

        created = memberContactRepository.save(toCreate);

        log.debug("Created member contact {}", created);

        return created;
    }

    @Override
    public final MemberProfile delete(final long number) {
        final MemberProfile existing;

        log.debug("Deleting member contact {}", number);

        existing = memberContactRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing member contact {}", number);
                throw new MissingMemberException(number);
            });

        memberContactRepository.delete(number);

        log.debug("Deleted member contact {}", number);

        return existing;
    }

    @Override
    public final Page<MemberProfile> getAll(final MemberFilter filter, final Pagination pagination,
            final Sorting sorting) {
        final Page<MemberProfile> memberContacts;

        log.debug("Reading member contacts with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        memberContacts = memberContactRepository.findAll(filter, pagination, sorting);

        log.debug("Read member contacts with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            memberContacts);

        return memberContacts;
    }

    @Override
    public final Optional<MemberProfile> getOne(final long number) {
        final Optional<MemberProfile> memberContacts;

        log.debug("Reading member contact {}", number);

        memberContacts = memberContactRepository.findOne(number);
        if (memberContacts.isEmpty()) {
            log.error("Missing member contact {}", number);
            throw new MissingMemberException(number);
        }

        log.debug("Read member contact {}: {}", number, memberContacts);

        return memberContacts;
    }

    @Override
    public final MemberProfile patch(final MemberProfile memberContact) {
        final MemberProfile existing;
        final MemberProfile toSave;
        final MemberProfile saved;

        log.debug("Patching member contact {} using data {}", memberContact.number(), memberContact);

        existing = memberContactRepository.findOne(memberContact.number())
            .orElseThrow(() -> {
                log.error("Missing member contact {}", memberContact.number());
                throw new MissingMemberException(memberContact.number());
            });

        toSave = copy(existing, memberContact);

        saved = memberContactRepository.save(toSave);

        log.debug("Patched member contact {}: {}", memberContact.number(), saved);

        return saved;
    }

    @Override
    public final MemberProfile update(final MemberProfile memberContact) {
        final MemberProfile saved;

        log.debug("Updating member contact {} using data {}", memberContact.number(), memberContact);

        if (!memberContactRepository.exists(memberContact.number())) {
            log.error("Missing member contact {}", memberContact.number());
            throw new MissingMemberException(memberContact.number());
        }

        saved = memberContactRepository.save(memberContact);

        log.debug("Updated member contact {}: {}", memberContact.number(), saved);

        return saved;
    }

    private final MemberProfile copy(final MemberProfile existing, final MemberProfile updated) {
        final ProfileName name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new ProfileName(Optional.ofNullable(updated.name()
                .firstName())
                .orElse(existing.name()
                    .firstName()),
                Optional.ofNullable(updated.name()
                    .lastName())
                    .orElse(existing.name()
                        .lastName()));
        }
        return new MemberProfile(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            Optional.ofNullable(updated.contactChannels())
                .orElse(existing.contactChannels()),
            Optional.ofNullable(updated.comments())
                .orElse(existing.comments()),
            Optional.ofNullable(updated.active())
                .orElse(existing.active()),
            Optional.ofNullable(updated.renew())
                .orElse(existing.renew()),
            Optional.ofNullable(updated.types())
                .orElse(existing.types()));
    }

}
