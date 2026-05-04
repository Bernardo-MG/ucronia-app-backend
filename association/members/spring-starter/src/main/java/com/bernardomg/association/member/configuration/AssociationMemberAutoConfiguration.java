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

package com.bernardomg.association.member.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.member.adapter.inbound.event.ActivateMemberOnFeePaidEventListener;
import com.bernardomg.association.member.adapter.inbound.event.ApplyRenewalOnMonthStartEventListener;
import com.bernardomg.association.member.adapter.inbound.event.DeactivateMemberOnFeeDeletedEventListener;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberContactMethodRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberCountRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberFeeTypeRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberProfileRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMembershipEvolutionRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaPublicMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberContactMethodSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberFeeTypeSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberInnerProfileSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MembershipEvolutionSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.PublicMemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.ReadMemberSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberCountRepository;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.usecase.service.DefaultMemberCountService;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.member.usecase.service.DefaultMemberStatusService;
import com.bernardomg.association.member.usecase.service.DefaultMembershipEvolutionService;
import com.bernardomg.association.member.usecase.service.DefaultProfileMembershipService;
import com.bernardomg.association.member.usecase.service.DefaultPublicMemberService;
import com.bernardomg.association.member.usecase.service.MemberCountService;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.member.usecase.service.MemberStatusService;
import com.bernardomg.association.member.usecase.service.MembershipEvolutionService;
import com.bernardomg.association.member.usecase.service.ProfileMembershipService;
import com.bernardomg.association.member.usecase.service.PublicMemberService;

@Configuration
@ComponentScan({ "com.bernardomg.association.member.adapter.outbound.rest.controller",
        "com.bernardomg.association.member.adapter.inbound.jpa" })
public class AssociationMemberAutoConfiguration {

    @Bean("activateMemberOnFeePaidEventListener")
    public ActivateMemberOnFeePaidEventListener
            getActivateMemberOnFeePaidEventListener(final MemberStatusService service) {
        return new ActivateMemberOnFeePaidEventListener(service);
    }

    @Bean("applyRenewalOnMonthStartEventListener")
    public ApplyRenewalOnMonthStartEventListener
            getApplyRenewalOnMonthStartEventListener(final MemberStatusService service) {
        return new ApplyRenewalOnMonthStartEventListener(service);
    }

    @Bean("deactivateMemberOnFeeDeletedEventListener")
    public DeactivateMemberOnFeeDeletedEventListener
            getDeactivateMemberOnFeeDeletedEventListener(final MemberStatusService service) {
        return new DeactivateMemberOnFeeDeletedEventListener(service);
    }

    @Bean("memberContactMethodRepository")
    public MemberContactMethodRepository
            getMemberContactMethodRepository(final MemberContactMethodSpringRepository contactMethodSpringRepository) {
        return new JpaMemberContactMethodRepository(contactMethodSpringRepository);
    }

    @Bean("memberCountRepository")
    public MemberCountRepository getMemberCountRepository(final PublicMemberSpringRepository memberSpringRepository) {
        return new JpaMemberCountRepository(memberSpringRepository);
    }

    @Bean("memberCountService")
    public MemberCountService getMemberCountService(final MemberCountRepository memberCountRepository) {
        return new DefaultMemberCountService(memberCountRepository);
    }

    @Bean("memberFeeTypeRepository")
    public MemberFeeTypeRepository
            getMemberFeeTypeRepository(final MemberFeeTypeSpringRepository memberFeeTypeSpringRepository) {
        return new JpaMemberFeeTypeRepository(memberFeeTypeSpringRepository);
    }

    @Bean("memberProfileRepository")
    public MemberProfileRepository
            getMemberProfileRepository(final MemberInnerProfileSpringRepository guestProfileSpringRepository) {
        return new JpaMemberProfileRepository(guestProfileSpringRepository);
    }

    @Bean("memberRepository")
    public MemberRepository getMemberRepository(final ReadMemberSpringRepository readMemberSpringRepository,
            final MemberSpringRepository memberSpringRepo,
            final MemberContactMethodSpringRepository contactMethodSpringRepository,
            final MemberFeeTypeSpringRepository memberFeeTypeSpringRepository) {
        return new JpaMemberRepository(readMemberSpringRepository, memberSpringRepo, contactMethodSpringRepository,
            memberFeeTypeSpringRepository);
    }

    @Bean("memberService")
    public MemberService getMemberService(final MemberRepository memberRepository,
            final MemberContactMethodRepository contactMethodRepository,
            final MemberFeeTypeRepository memberFeeTypeRepository) {
        return new DefaultMemberService(memberRepository, contactMethodRepository, memberFeeTypeRepository);
    }

    @Bean("memberService")
    public PublicMemberService getMemberService(final PublicMemberRepository memberRepository) {
        return new DefaultPublicMemberService(memberRepository);
    }

    @Bean("membershipEvolutionRepository")
    public MembershipEvolutionRepository getMembershipEvolutionRepository(
            final MembershipEvolutionSpringRepository membershipEvolutionSpringRepository) {
        return new JpaMembershipEvolutionRepository(membershipEvolutionSpringRepository);
    }

    @Bean("membershipEvolutionService")
    public MembershipEvolutionService
            getMembershipEvolutionService(final MembershipEvolutionRepository membershipEvolutionRepository) {
        return new DefaultMembershipEvolutionService(membershipEvolutionRepository);
    }

    @Bean("memberStatusService")
    public MemberStatusService getMemberStatusService(final MemberRepository memberRepository) {
        return new DefaultMemberStatusService(memberRepository);
    }

    @Bean("profileMembershipService")
    public ProfileMembershipService getProfileMembershipService(final MemberRepository memberRepository,
            final MemberProfileRepository memberProfileRepository,
            final MemberFeeTypeRepository memberFeeTypeRepository) {
        return new DefaultProfileMembershipService(memberRepository, memberProfileRepository, memberFeeTypeRepository);
    }

    @Bean("publicMemberRepository")
    public PublicMemberRepository getPublicMemberRepository(final PublicMemberSpringRepository memberSpringRepository) {
        return new JpaPublicMemberRepository(memberSpringRepository);
    }

}
