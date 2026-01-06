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

import com.bernardomg.association.member.domain.exception.MemberExistsException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
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
    private static final Logger     log = LoggerFactory.getLogger(DefaultProfileMembershipService.class);

    private final MemberRepository  memberRepository;

    private final ProfileRepository profileRepository;

    public DefaultProfileMembershipService(final MemberRepository memberRepo, final ProfileRepository profileRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
        profileRepository = Objects.requireNonNull(profileRepo);
    }

    @Override
    public final Member convertToMember(final long number, final long feeType) {
        final Profile        existing;
        final Member         toCreate;
        final Member         created;
        final Member.FeeType memberFeeType;

        log.debug("Converting profile {} to member", number);

        existing = profileRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing profile {}", number);
                throw new MissingProfileException(number);
            });

        if (memberRepository.exists(number)) {
            throw new MemberExistsException(number);
        }

        memberFeeType = new Member.FeeType(feeType);
        toCreate = new Member(existing.number(), memberFeeType, existing.name(), true, true);

        created = memberRepository.save(toCreate, number);

        log.debug("Converted profile {} to member", number);

        return created;
    }

}
