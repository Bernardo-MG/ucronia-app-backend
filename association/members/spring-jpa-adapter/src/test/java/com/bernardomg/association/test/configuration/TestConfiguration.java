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

package com.bernardomg.association.test.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTypeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeSummaryRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeTypeRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeSummaryRepository;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberProfileRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberSummaryRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMembershipEvolutionRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MembershipEvolutionSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.domain.repository.MemberSummaryRepository;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;

@Configuration
@EnableJpaRepositories(basePackages = { "com.bernardomg.association.member.adapter.inbound.jpa",
        "com.bernardomg.association.fee.adapter.inbound.jpa",
        "com.bernardomg.association.transaction.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa" })
@EntityScan(basePackages = { "com.bernardomg.association.member.adapter.inbound.jpa",
        "com.bernardomg.association.fee.adapter.inbound.jpa",
        "com.bernardomg.association.transaction.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa" })
public class TestConfiguration {

    @Bean("feeRepository")
    public FeeRepository getFeeRepository(final FeeSpringRepository feeSpringRepository,
            final MemberProfileSpringRepository memberProfileSpringRepository,
            final FeeTypeSpringRepository feeTypeSpringRepository,
            final TransactionSpringRepository transactionSpringRepository) {
        return new JpaFeeRepository(feeSpringRepository, memberProfileSpringRepository, feeTypeSpringRepository,
            transactionSpringRepository);
    }

    @Bean("feeSummaryRepository")
    public FeeSummaryRepository getFeeSummaryRepository(final FeeSpringRepository feeSpringRepository) {
        return new JpaFeeSummaryRepository(feeSpringRepository);
    }

    @Bean("feeTypeRepository")
    public FeeTypeRepository getFeeTypeRepository(final FeeTypeSpringRepository feeTypeSpringRepository) {
        return new JpaFeeTypeRepository(feeTypeSpringRepository);
    }

    @Bean("memberProfileRepository")
    public MemberProfileRepository getMemberProfileRepository(
            final MemberProfileSpringRepository updateMemberProfileSpringRepository,
            final ContactMethodSpringRepository contactMethodSpringRepository,
            final ProfileSpringRepository profileSpringRepository,
            final FeeTypeSpringRepository feeTypeSpringRepository) {
        return new JpaMemberProfileRepository(updateMemberProfileSpringRepository, contactMethodSpringRepository,
            profileSpringRepository, feeTypeSpringRepository);
    }

    @Bean("memberRepository")
    public MemberRepository getMemberRepository(final MemberSpringRepository memberSpringRepository) {
        return new JpaMemberRepository(memberSpringRepository);
    }

    @Bean("membershipEvolutionRepository")
    public MembershipEvolutionRepository getMembershipEvolutionRepository(
            final MembershipEvolutionSpringRepository membershipEvolutionSpringRepository) {
        return new JpaMembershipEvolutionRepository(membershipEvolutionSpringRepository);
    }

    @Bean("memberSummaryRepository")
    public MemberSummaryRepository getMemberSummaryRepository(final MemberSpringRepository memberSpringRepository) {
        return new JpaMemberSummaryRepository(memberSpringRepository);
    }

}
