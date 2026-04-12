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
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberProfileRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberSummaryRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMembershipEvolutionRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberFeeTypeSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MembershipEvolutionSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.domain.repository.MemberSummaryRepository;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.usecase.service.DefaultMemberProfileService;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.member.usecase.service.DefaultMemberStatusService;
import com.bernardomg.association.member.usecase.service.DefaultMemberSummaryService;
import com.bernardomg.association.member.usecase.service.DefaultMembershipEvolutionService;
import com.bernardomg.association.member.usecase.service.DefaultProfileMembershipService;
import com.bernardomg.association.member.usecase.service.MemberProfileService;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.member.usecase.service.MemberStatusService;
import com.bernardomg.association.member.usecase.service.MemberSummaryService;
import com.bernardomg.association.member.usecase.service.MembershipEvolutionService;
import com.bernardomg.association.member.usecase.service.ProfileMembershipService;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;

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

    @Bean("memberProfileRepository")
    public MemberProfileRepository getMemberProfileRepository(
            final MemberProfileSpringRepository updateMemberProfileSpringRepository,
            final ContactMethodSpringRepository contactMethodSpringRepository,
            final ProfileSpringRepository profileSpringRepository,
            final MemberFeeTypeSpringRepository memberFeeTypeSpringRepository) {
        return new JpaMemberProfileRepository(updateMemberProfileSpringRepository, contactMethodSpringRepository,
            profileSpringRepository, memberFeeTypeSpringRepository);
    }

    @Bean("memberProfileService")
    public MemberProfileService getMemberProfileService(final MemberProfileRepository memberProfileRepository,
            final ContactMethodRepository contactMethodRepository,
            final MemberFeeTypeRepository memberFeeTypeRepository) {
        return new DefaultMemberProfileService(memberProfileRepository, contactMethodRepository,
            memberFeeTypeRepository);
    }

    @Bean("memberRepository")
    public MemberRepository getMemberRepository(final MemberSpringRepository memberSpringRepository) {
        return new JpaMemberRepository(memberSpringRepository);
    }

    @Bean("memberService")
    public MemberService getMemberService(final MemberRepository memberRepository) {
        return new DefaultMemberService(memberRepository);
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
    public MemberStatusService getMemberStatusService(final MemberProfileRepository memberProfileRepository) {
        return new DefaultMemberStatusService(memberProfileRepository);
    }

    @Bean("memberSummaryRepository")
    public MemberSummaryRepository getMemberSummaryRepository(final MemberSpringRepository memberSpringRepository) {
        return new JpaMemberSummaryRepository(memberSpringRepository);
    }

    @Bean("memberSummaryService")
    public MemberSummaryService getMemberSummaryService(final MemberSummaryRepository memberSummaryRepository) {
        return new DefaultMemberSummaryService(memberSummaryRepository);
    }

    @Bean("profileMembershipService")
    public ProfileMembershipService getProfileMembershipService(final MemberProfileRepository memberProfileRepository,
            final ProfileRepository profileRepository, final MemberFeeTypeRepository memberFeeTypeRepository) {
        return new DefaultProfileMembershipService(memberProfileRepository, profileRepository, memberFeeTypeRepository);
    }

}
