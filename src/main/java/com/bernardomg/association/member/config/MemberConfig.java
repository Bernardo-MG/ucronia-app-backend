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

package com.bernardomg.association.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.member.adapter.inbound.jpa.repository.JpaMemberBalanceRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MonthlyMemberBalanceSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.security.register.MemberPermissionRegister;
import com.bernardomg.association.member.usecase.service.DefaultMemberBalanceService;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.member.usecase.service.DefaultReducedMemberService;
import com.bernardomg.association.member.usecase.service.MemberBalanceService;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.member.usecase.service.ReducedMemberService;

/**
 * Member configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class MemberConfig {

    public MemberConfig() {
        super();
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

    @Bean("memberPermissionRegister")
    public MemberPermissionRegister getMemberPermissionRegister() {
        return new MemberPermissionRegister();
    }

    @Bean("memberService")
    public MemberService getMemberService(final MemberRepository memberRepository) {
        return new DefaultMemberService(memberRepository);
    }

    @Bean("reducedMemberService")
    public ReducedMemberService getReducedMemberService(final MemberRepository memberRepository) {
        return new DefaultReducedMemberService(memberRepository);
    }

}
