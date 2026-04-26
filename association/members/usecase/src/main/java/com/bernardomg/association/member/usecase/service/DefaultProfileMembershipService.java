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

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.member.domain.exception.MemberExistsException;
import com.bernardomg.association.member.domain.exception.MissingMemberFeeTypeException;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the profile to member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultProfileMembershipService implements ProfileMembershipService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultProfileMembershipService.class);

    private final MemberFeeTypeRepository memberFeeTypeRepository;

    private final MemberProfileRepository memberProfileRepository;

    private final ProfileRepository       profileRepository;

    public DefaultProfileMembershipService(final MemberProfileRepository memberProfileRepo,
            final ProfileRepository profileRepo, final MemberFeeTypeRepository memberFeeTypeRepo) {
        super();

        memberProfileRepository = Objects.requireNonNull(memberProfileRepo);
        profileRepository = Objects.requireNonNull(profileRepo);
        memberFeeTypeRepository = Objects.requireNonNull(memberFeeTypeRepo);
    }

    @Override
    public final MemberProfile convertToMember(final long number, final long feeType) {
        final Profile                                  existing;
        final MemberProfile                            toCreate;
        final MemberProfile                            created;
        final MemberProfile.FeeType                    memberFeeType;
        final Collection<MemberProfile.ContactChannel> contactChannels;
        final MemberProfile.Name                       name;

        log.debug("Converting profile {} to member", number);

        existing = profileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing profile {}", number);
                throw new MissingProfileException(number);
            });

        if (memberProfileRepository.exists(number)) {
            log.error("Missing member {}", number);
            throw new MemberExistsException(number);
        }

        if (!memberFeeTypeRepository.exists(feeType)) {
            log.error("Missing fee type {}", feeType);
            throw new MissingMemberFeeTypeException(feeType);
        }

        contactChannels = existing.contactChannels()
            .stream()
            .map(this::toMemberContactChannel)
            .toList();
        memberFeeType = new MemberProfile.FeeType(feeType, "", 0f);
        name = new MemberProfile.Name(existing.name()
            .firstName(),
            existing.name()
                .lastName());
        toCreate = new MemberProfile(existing.identifier(), existing.number(), name, existing.birthDate(),
            contactChannels, existing.address(), existing.comments(), true, true, memberFeeType, existing.types());

        created = memberProfileRepository.save(toCreate);

        log.debug("Converted profile {} to member", number);

        return created;
    }

    private final MemberProfile.ContactChannel toMemberContactChannel(final Profile.ContactChannel contactChannel) {
        final MemberProfile.ContactMethod contactMethod;

        contactMethod = new MemberProfile.ContactMethod(contactChannel.contactMethod()
            .number(),
            contactChannel.contactMethod()
                .name());
        return new MemberProfile.ContactChannel(contactMethod, contactChannel.detail());
    }

}
