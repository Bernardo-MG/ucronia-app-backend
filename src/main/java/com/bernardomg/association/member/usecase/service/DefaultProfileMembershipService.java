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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.member.domain.exception.MemberExistsException;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;

/**
 * Default implementation of the profile to member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultProfileMembershipService implements ProfileMembershipService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultProfileMembershipService.class);

    private final FeeTypeRepository       feeTypeRepository;

    private final MemberProfileRepository memberProfileRepository;

    private final ProfileRepository       profileRepository;

    public DefaultProfileMembershipService(final MemberProfileRepository memberProfileRepo,
            final ProfileRepository profileRepo, final FeeTypeRepository feeTypeRepo) {
        super();

        memberProfileRepository = Objects.requireNonNull(memberProfileRepo);
        profileRepository = Objects.requireNonNull(profileRepo);
        feeTypeRepository = Objects.requireNonNull(feeTypeRepo);
    }

    @Override
    public final MemberProfile convertToMember(final long number, final long feeType) {
        final Profile               existing;
        final MemberProfile         toCreate;
        final MemberProfile         created;
        final MemberProfile.FeeType memberFeeType;

        log.debug("Converting profile {} to member", number);

        // TODO: check the fee type exists

        existing = profileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing profile {}", number);
                throw new MissingProfileException(number);
            });

        if (memberProfileRepository.exists(number)) {
            throw new MemberExistsException(number);
        }

        if (!feeTypeRepository.exists(feeType)) {
            throw new MissingFeeTypeException(number);
        }

        memberFeeType = new MemberProfile.FeeType(feeType);
        toCreate = new MemberProfile(existing.identifier(), existing.number(), existing.name(), existing.birthDate(),
            existing.contactChannels(), existing.address(), existing.comments(), true, true, memberFeeType,
            existing.types());

        created = memberProfileRepository.save(toCreate, number);

        log.debug("Converted profile {} to member", number);

        return created;
    }

}
