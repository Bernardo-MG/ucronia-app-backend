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

import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberContactMethodRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberCountRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberFeeTypeRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberProfileRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMembershipEvolutionRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaPublicMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberContactMethodSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberFeeTypeSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MembershipEvolutionSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.PublicMemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.ReadMemberProfileSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberCountRepository;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;

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

    @Bean("memberContactMethodRepository")
    public MemberContactMethodRepository
            getMemberContactMethodRepository(final MemberContactMethodSpringRepository contactMethodSpringRepository) {
        return new JpaMemberContactMethodRepository(contactMethodSpringRepository);
    }

    @Bean("memberCountRepository")
    public MemberCountRepository getMemberCountRepository(final PublicMemberSpringRepository memberSpringRepository) {
        return new JpaMemberCountRepository(memberSpringRepository);
    }

    @Bean("memberFeeTypeRepository")
    public MemberFeeTypeRepository
            getMemberFeeTypeRepository(final MemberFeeTypeSpringRepository memberFeeTypeSpringRepository) {
        return new JpaMemberFeeTypeRepository(memberFeeTypeSpringRepository);
    }

    @Bean("memberProfileRepository")
    public MemberProfileRepository getMemberProfileRepository(
            final ReadMemberProfileSpringRepository updateMemberProfileSpringRepository,
            final MemberProfileSpringRepository memberProfileSpringRepo,
            final MemberContactMethodSpringRepository memberContactMethodSpringRepository,
            final MemberFeeTypeSpringRepository memberFeeTypeSpringRepository) {
        return new JpaMemberProfileRepository(updateMemberProfileSpringRepository, memberProfileSpringRepo,
            memberContactMethodSpringRepository, memberFeeTypeSpringRepository);
    }

    @Bean("membershipEvolutionRepository")
    public MembershipEvolutionRepository getMembershipEvolutionRepository(
            final MembershipEvolutionSpringRepository membershipEvolutionSpringRepository) {
        return new JpaMembershipEvolutionRepository(membershipEvolutionSpringRepository);
    }

    @Bean("publicMemberRepository")
    public PublicMemberRepository getPublicMemberRepository(final PublicMemberSpringRepository memberSpringRepository) {
        return new JpaPublicMemberRepository(memberSpringRepository);
    }

}
