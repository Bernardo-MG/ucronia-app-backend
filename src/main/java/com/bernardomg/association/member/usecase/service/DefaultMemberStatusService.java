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

import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;

@Service
@Transactional
public final class DefaultMemberStatusService implements MemberStatusService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultMemberStatusService.class);

    private final MemberContactRepository memberContactRepository;

    public DefaultMemberStatusService(final MemberContactRepository memberContactRepo) {
        super();

        memberContactRepository = Objects.requireNonNull(memberContactRepo);
    }

    @Override
    public final void activate(final YearMonth date, final Long memberNumber) {
        final Optional<MemberContact> member;
        final MemberContact           activated;

        if (YearMonth.now()
            .equals(date)) {
            log.debug("Activating member {}", memberNumber);
            member = memberContactRepository.findOne(memberNumber);

            if (member.isEmpty()) {
                log.warn("Missing member {}", memberNumber);
                // TODO: no exception?
            } else {
                activated = activated(member.get());
                memberContactRepository.save(activated);

                log.debug("Activated member {}", memberNumber);
            }
        }
    }

    @Override
    public final void applyRenewal() {
        final Collection<MemberContact> members;
        final Collection<MemberContact> toActivate;
        final Collection<MemberContact> toDeactivate;
        final Collection<MemberContact> toSave;

        log.debug("Applying membership renewals");

        members = memberContactRepository.findAllWithRenewalMismatch();

        toActivate = members.stream()
            .filter(p -> !p.active())
            .map(this::activated)
            .toList();

        toDeactivate = members.stream()
            .filter(MemberContact::active)
            .map(this::deactivated)
            .toList();

        toSave = Stream.concat(toActivate.stream(), toDeactivate.stream())
            .toList();
        memberContactRepository.saveAll(toSave);

        log.debug("Applied membership renewals to {}", toSave);
    }

    @Override
    public final void deactivate(final YearMonth date, final Long memberNumber) {
        final Optional<MemberContact> member;
        final MemberContact           deactivated;

        // If deleting at the current month, the user is set to inactive
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Deactivating member {}", memberNumber);
            member = memberContactRepository.findOne(memberNumber);

            if (member.isEmpty()) {
                log.warn("Missing member {}", memberNumber);
                // TODO: no exception?
            } else {
                deactivated = deactivated(member.get());
                memberContactRepository.save(deactivated);

                log.debug("Deactivated member {}", memberNumber);
            }
        }
    }

    private final MemberContact activated(final MemberContact original) {
        return new MemberContact(original.identifier(), original.number(), original.name(), original.birthDate(), true,
            true, original.contactChannels());
    }

    private final MemberContact deactivated(final MemberContact original) {
        return new MemberContact(original.identifier(), original.number(), original.name(), original.birthDate(), false,
            false, original.contactChannels());
    }

}
