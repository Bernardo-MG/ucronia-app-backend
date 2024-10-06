/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.member.adapter.inbound.event.FeePaidEventListener;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberBalanceRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaPublicMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MonthlyMemberBalanceSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.security.register.MemberPermissionRegister;
import com.bernardomg.association.member.usecase.service.DefaultMemberBalanceService;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.member.usecase.service.DefaultMemberStatusService;
import com.bernardomg.association.member.usecase.service.DefaultPublicMemberService;
import com.bernardomg.association.member.usecase.service.MemberBalanceService;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.member.usecase.service.MemberStatusService;
import com.bernardomg.association.member.usecase.service.PublicMemberService;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;

/**
 * Member configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class MemberConfiguration {

    public MemberConfiguration() {
        super();
    }

    @Bean("feePaidEventListener")
    public FeePaidEventListener getFeePaidEventListener(final MemberStatusService service) {
        return new FeePaidEventListener(service);
    }

    @Bean("memberBalanceRepository")
    public MemberBalanceRepository
            getMemberBalanceRepository(final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepository) {
        return new JpaMemberBalanceRepository(monthlyMemberBalanceRepository);
    }

    @Bean("memberBalanceService")
    public MemberBalanceService getMemberBalanceService(final MemberBalanceRepository memberBalanceRepository) {
        return new DefaultMemberBalanceService(memberBalanceRepository);
    }

    @Bean("memberStatusService")
    public MemberStatusService getMemberFeeService(final MemberRepository memberRepository) {
        return new DefaultMemberStatusService(memberRepository);
    }

    @Bean("memberPermissionRegister")
    public MemberPermissionRegister getMemberPermissionRegister() {
        return new MemberPermissionRegister();
    }

    @Bean("memberRepository")
    public MemberRepository getMemberRepository(final MemberSpringRepository memberSpringRepo,
            final PersonSpringRepository personSpringRepository) {
        return new JpaMemberRepository(memberSpringRepo, personSpringRepository);
    }

    @Bean("memberService")
    public MemberService getMemberService(final MemberRepository memberRepository) {
        return new DefaultMemberService(memberRepository);
    }

    @Bean("publicMemberRepository")
    public PublicMemberRepository getPublicMemberRepository(final MemberSpringRepository memberSpringRepo) {
        return new JpaPublicMemberRepository(memberSpringRepo);
    }

    @Bean("publicMemberService")
    public PublicMemberService getPublicMemberService(final PublicMemberRepository memberRepository) {
        return new DefaultPublicMemberService(memberRepository);
    }

}
