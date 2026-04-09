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

package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeMemberEntityMapper;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntityMapper;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;

@Transactional
public final class JpaFeeMemberRepository implements FeeMemberRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaFeeMemberRepository.class);

    private final MemberProfileSpringRepository memberProfileSpringRepository;

    public JpaFeeMemberRepository(final MemberProfileSpringRepository memberProfileSpringRepo) {
        super();

        memberProfileSpringRepository = Objects.requireNonNull(memberProfileSpringRepo);
    }

    @Override
    public final Optional<FeeType> findFeeType(final Long number) {
        final Optional<FeeType> feeType;

        log.trace("Finding fee type for member {}", number);

        feeType = memberProfileSpringRepository.findByNumber(number)
            .map(MemberProfileEntity::getFeeType)
            .map(FeeTypeEntityMapper::toDomain);

        log.trace("Found fee type for member {}: {}", number, feeType);

        return feeType;
    }

    @Override
    public final Optional<FeeMember> findOne(final Long number) {
        final Optional<FeeMember> memberProfile;

        log.trace("Finding member profile with number {}", number);

        memberProfile = memberProfileSpringRepository.findByNumber(number)
            .map(FeeMemberEntityMapper::toDomain);

        log.trace("Found member profile with number {}: {}", number, memberProfile);

        return memberProfile;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = memberProfileSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

}
