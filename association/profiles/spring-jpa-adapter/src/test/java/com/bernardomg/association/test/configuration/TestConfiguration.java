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
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;

import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeAssignedProfileSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTransactionSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTypeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeProfileRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeSummaryRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeTransactionRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeTypeRepository;
import com.bernardomg.association.fee.domain.repository.FeeProfileRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeSummaryRepository;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.GuestSpringRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.JpaGuestRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.ReadGuestSpringRepository;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberContactMethodRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberCountRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMembershipEvolutionRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaPublicMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberContactMethodSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.PublicMemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.ReadMemberSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberCountRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.domain.repository.MembershipEvolutionRepository;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.JpaContactMethodRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.JpaProfileRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.JpaSponsorRepository;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.ReadSponsorSpringRepository;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.SponsorSpringRepository;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;

@Configuration
@EnableJpaRepositories(basePackages = { "com.bernardomg.association.member.adapter.inbound.jpa",
        "com.bernardomg.association.fee.adapter.inbound.jpa",
        "com.bernardomg.association.transaction.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa",
        "com.bernardomg.association.sponsor.adapter.inbound.jpa",
        "com.bernardomg.association.guest.adapter.inbound.jpa", "com.bernardomg.security.adapter.inbound.jpa" })
@EntityScan(basePackages = { "com.bernardomg.association.member.adapter.inbound.jpa",
        "com.bernardomg.association.fee.adapter.inbound.jpa",
        "com.bernardomg.association.transaction.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa",
        "com.bernardomg.association.sponsor.adapter.inbound.jpa",
        "com.bernardomg.association.guest.adapter.inbound.jpa", "com.bernardomg.security.adapter.inbound.jpa" })
public class TestConfiguration {

    @Bean("authenticationTrustResolver")
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Bean("contactMethodRepository")
    public ContactMethodRepository
            getContactMethodRepository(final ContactMethodSpringRepository contactMethodSpringRepository) {
        return new JpaContactMethodRepository(contactMethodSpringRepository);
    }

    @Bean("feeProfileRepository")
    public FeeProfileRepository
            getFeeProfileRepository(final FeeAssignedProfileSpringRepository feeProfileSpringRepository) {
        return new JpaFeeProfileRepository(feeProfileSpringRepository);
    }

    @Bean("feeRepository")
    public FeeRepository getFeeRepository(final FeeSpringRepository feeSpringRepository,
            final MemberSpringRepository memberSpringRepository, final FeeTypeSpringRepository feeTypeSpringRepository,
            final FeeTransactionSpringRepository transactionSpringRepository) {
        return new JpaFeeRepository(feeSpringRepository, memberSpringRepository, feeTypeSpringRepository,
            transactionSpringRepository);
    }

    @Bean("feeSummaryRepository")
    public FeeSummaryRepository getFeeSummaryRepository(final FeeSpringRepository feeSpringRepository) {
        return new JpaFeeSummaryRepository(feeSpringRepository);
    }

    @Bean("feeTransactionRepository")
    public FeeTransactionRepository
            getFeeTransactionRepository(final FeeTransactionSpringRepository transactionRepository) {
        return new JpaFeeTransactionRepository(transactionRepository);
    }

    @Bean("feeTypeRepository")
    public FeeTypeRepository getFeeTypeRepository(final FeeTypeSpringRepository feeTypeSpringRepository) {
        return new JpaFeeTypeRepository(feeTypeSpringRepository);
    }

    @Bean("guestRepository")
    public GuestRepository getGuestRepository(final GuestSpringRepository guestSpringRepository,
            final ReadGuestSpringRepository readGuestSpringRepository,
            final ProfileSpringRepository profileSpringRepository,
            final ContactMethodSpringRepository contactMethodSpringRepository) {
        return new JpaGuestRepository(guestSpringRepository, readGuestSpringRepository, profileSpringRepository,
            contactMethodSpringRepository);
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

    @Bean("MemberRepository")
    public MemberRepository getMemberRepository(final ReadMemberSpringRepository readMemberSpringRepository,
            final MemberSpringRepository memberSpringRepository,
            final MemberContactMethodSpringRepository memberContactMethodSpringRepository,
            final FeeTypeSpringRepository feeTypeSpringRepository) {
        return new JpaMemberRepository(readMemberSpringRepository, memberSpringRepository,
            memberContactMethodSpringRepository, feeTypeSpringRepository);
    }

    @Bean("membershipEvolutionRepository")
    public MembershipEvolutionRepository
            getMembershipEvolutionRepository(final MemberSpringRepository memberSpringRepository) {
        return new JpaMembershipEvolutionRepository(memberSpringRepository);
    }

    @Bean("profileRepository")
    public ProfileRepository getProfileRepository(final ProfileSpringRepository profileSpringRepository,
            final ContactMethodSpringRepository contactMethodSpringRepository) {
        return new JpaProfileRepository(profileSpringRepository, contactMethodSpringRepository);
    }

    @Bean("publicMemberRepository")
    public PublicMemberRepository getPublicMemberRepository(final PublicMemberSpringRepository memberSpringRepository) {
        return new JpaPublicMemberRepository(memberSpringRepository);
    }

    @Bean("sponsorRepository")
    public SponsorRepository getSponsorRepository(final SponsorSpringRepository sponsorSpringRepository,
            final ReadSponsorSpringRepository readSponsorSpringRepository,
            final ContactMethodSpringRepository contactMethodSpringRepository,
            final ProfileSpringRepository profileSpringRepository) {
        return new JpaSponsorRepository(sponsorSpringRepository, readSponsorSpringRepository,
            contactMethodSpringRepository, profileSpringRepository);
    }

}
