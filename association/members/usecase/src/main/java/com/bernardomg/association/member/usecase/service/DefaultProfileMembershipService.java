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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.member.domain.exception.MemberExistsException;
import com.bernardomg.association.member.domain.exception.MissingMemberFeeTypeException;
import com.bernardomg.association.member.domain.exception.MissingMemberProfileException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;

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

    private final MemberRepository        memberRepository;

    public DefaultProfileMembershipService(final MemberRepository memberRepo,
            final MemberFeeTypeRepository memberFeeTypeRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
        memberFeeTypeRepository = Objects.requireNonNull(memberFeeTypeRepo);
    }

    @Override
    public final Member convertToMember(final long number, final long feeType) {
        final Member                            existing;
        final Member                            toCreate;
        final Member                            created;
        final Member.FeeType                    memberFeeType;
        final Collection<Member.ContactChannel> contactChannels;
        final Member.Name                       name;
        final Set<String>                       types;

        log.debug("Converting profile {} to member", number);

        existing = memberRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing profile {}", number);
                throw new MissingMemberProfileException(number);
            });

        if (memberRepository.exists(number)) {
            log.error("Member {} already exists", number);
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
        memberFeeType = new Member.FeeType(feeType, "", 0f);
        name = new Member.Name(existing.name()
            .firstName(),
            existing.name()
                .lastName());
        types = Stream.concat(existing.types()
            .stream(), Stream.of(Member.PROFILE_TYPE))
            .collect(Collectors.toSet());
        toCreate = new Member(existing.identifier(), existing.number(), name, existing.birthDate(), contactChannels,
            existing.address(), existing.comments(), true, true, memberFeeType, types);

        created = memberRepository.save(toCreate);

        log.debug("Converted profile {} to member", number);

        return created;
    }

    private final Member.ContactChannel toMemberContactChannel(final Member.ContactChannel contactChannel) {
        final Member.ContactMethod contactMethod;

        contactMethod = new Member.ContactMethod(contactChannel.contactMethod()
            .number(),
            contactChannel.contactMethod()
                .name());
        return new Member.ContactChannel(contactMethod, contactChannel.detail());
    }

}
