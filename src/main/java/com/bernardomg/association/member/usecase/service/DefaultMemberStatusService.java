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

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;

@Service
@Transactional
public final class DefaultMemberStatusService implements MemberStatusService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultMemberStatusService.class);

    private final MemberProfileRepository memberProfileRepository;

    public DefaultMemberStatusService(final MemberProfileRepository memberProfileRepo) {
        super();

        memberProfileRepository = Objects.requireNonNull(memberProfileRepo);
    }

    @Override
    public final void activate(final YearMonth date, final Long memberNumber) {
        final Optional<MemberProfile> member;
        final MemberProfile           activated;

        if (YearMonth.now()
            .equals(date)) {
            log.debug("Activating member {}", memberNumber);
            member = memberProfileRepository.findOne(memberNumber);

            if (member.isEmpty()) {
                log.warn("Missing member {}", memberNumber);
                // TODO: no exception?
            } else {
                activated = activated(member.get());
                memberProfileRepository.save(activated);

                log.debug("Activated member {}", memberNumber);
            }
        }
    }

    @Override
    public final void applyRenewal() {
        final Collection<MemberProfile> members;
        final Collection<MemberProfile> toActivate;
        final Collection<MemberProfile> toDeactivate;
        final Collection<MemberProfile> toSave;

        log.debug("Applying membership renewals");

        members = memberProfileRepository.findAllWithRenewalMismatch();

        toActivate = members.stream()
            .filter(p -> !p.active())
            .map(this::activated)
            .toList();

        toDeactivate = members.stream()
            .filter(MemberProfile::active)
            .map(this::deactivated)
            .toList();

        toSave = Stream.concat(toActivate.stream(), toDeactivate.stream())
            .toList();
        memberProfileRepository.saveAll(toSave);

        log.debug("Applied membership renewals to {}", toSave);
    }

    @Override
    public final void deactivate(final YearMonth date, final Long memberNumber) {
        final Optional<MemberProfile> member;
        final MemberProfile           deactivated;

        // If deleting at the current month, the user is set to inactive
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Deactivating member {}", memberNumber);
            member = memberProfileRepository.findOne(memberNumber);

            if (member.isEmpty()) {
                log.warn("Missing member {}", memberNumber);
                // TODO: no exception?
            } else {
                deactivated = deactivated(member.get());
                memberProfileRepository.save(deactivated);

                log.debug("Deactivated member {}", memberNumber);
            }
        }
    }

    private final MemberProfile activated(final MemberProfile original) {
        return new MemberProfile(original.identifier(), original.number(), original.name(), original.birthDate(),
            original.contactChannels(), original.comments(), true, true, original.feeType(), original.types());
    }

    private final MemberProfile deactivated(final MemberProfile original) {
        return new MemberProfile(original.identifier(), original.number(), original.name(), original.birthDate(),
            original.contactChannels(), original.comments(), false, false, original.feeType(), original.types());
    }

}
