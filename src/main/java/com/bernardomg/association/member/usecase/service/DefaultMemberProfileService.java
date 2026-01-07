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
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the member profile service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultMemberProfileService implements MemberProfileService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultMemberProfileService.class);

    private final MemberProfileRepository memberProfileRepository;

    public DefaultMemberProfileService(final MemberProfileRepository memberProfileRepo) {
        super();

        memberProfileRepository = Objects.requireNonNull(memberProfileRepo);
    }

    @Override
    public final MemberProfile create(final MemberProfile memberProfile) {
        final MemberProfile toCreate;
        final MemberProfile created;

        log.debug("Creating member profile {}", memberProfile);

        toCreate = new MemberProfile(memberProfile.identifier(), 0L, memberProfile.name(), memberProfile.birthDate(),
            memberProfile.contactChannels(), memberProfile.comments(), memberProfile.active(), memberProfile.renew(),
            memberProfile.feeType(), memberProfile.types());

        created = memberProfileRepository.save(toCreate);

        log.debug("Created member profile {}", created);

        return created;
    }

    @Override
    public final MemberProfile delete(final long number) {
        final MemberProfile existing;

        log.debug("Deleting member profile {}", number);

        existing = memberProfileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing member profile {}", number);
                throw new MissingMemberException(number);
            });

        memberProfileRepository.delete(number);

        log.debug("Deleted member profile {}", number);

        return existing;
    }

    @Override
    public final Page<MemberProfile> getAll(final MemberFilter filter, final Pagination pagination,
            final Sorting sorting) {
        final Page<MemberProfile> memberProfiles;

        log.debug("Reading member contacts with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        memberProfiles = memberProfileRepository.findAll(filter, pagination, sorting);

        log.debug("Read member contacts with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            memberProfiles);

        return memberProfiles;
    }

    @Override
    public final Optional<MemberProfile> getOne(final long number) {
        final Optional<MemberProfile> memberProfiles;

        log.debug("Reading member profile {}", number);

        memberProfiles = memberProfileRepository.findOne(number);
        if (memberProfiles.isEmpty()) {
            log.error("Missing member profile {}", number);
            throw new MissingMemberException(number);
        }

        log.debug("Read member profile {}: {}", number, memberProfiles);

        return memberProfiles;
    }

    @Override
    public final MemberProfile patch(final MemberProfile memberProfile) {
        final MemberProfile existing;
        final MemberProfile toSave;
        final MemberProfile saved;

        log.debug("Patching member profile {} using data {}", memberProfile.number(), memberProfile);

        existing = memberProfileRepository.findOne(memberProfile.number())
            .orElseThrow(() -> {
                log.error("Missing member profile {}", memberProfile.number());
                throw new MissingMemberException(memberProfile.number());
            });

        toSave = copy(existing, memberProfile);

        saved = memberProfileRepository.save(toSave);

        log.debug("Patched member profile {}: {}", memberProfile.number(), saved);

        return saved;
    }

    @Override
    public final MemberProfile update(final MemberProfile memberProfile) {
        final MemberProfile saved;

        log.debug("Updating member profile {} using data {}", memberProfile.number(), memberProfile);

        if (!memberProfileRepository.exists(memberProfile.number())) {
            log.error("Missing member profile {}", memberProfile.number());
            throw new MissingMemberException(memberProfile.number());
        }

        saved = memberProfileRepository.save(memberProfile);

        log.debug("Updated member profile {}: {}", memberProfile.number(), saved);

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
            Optional.ofNullable(updated.feeType())
                .orElse(existing.feeType()),
            Optional.ofNullable(updated.types())
                .orElse(existing.types()));
    }

}
