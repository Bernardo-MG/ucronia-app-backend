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

package com.bernardomg.association.config.fee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.persistence.fee.repository.FeeRepository;
import com.bernardomg.association.persistence.fee.repository.MemberFeeRepository;
import com.bernardomg.association.persistence.member.repository.MemberRepository;
import com.bernardomg.association.persistence.member.repository.MonthlyMemberBalanceRepository;
import com.bernardomg.association.schedule.fee.FeeMaintenanceScheduleTask;
import com.bernardomg.association.service.fee.DefaultFeeMaintenanceService;
import com.bernardomg.association.service.fee.FeeMaintenanceService;
import com.bernardomg.association.service.member.DefaultMemberFeeCalendarService;
import com.bernardomg.association.service.member.DefaultMemberService;
import com.bernardomg.association.service.member.DefaultMembershipBalanceService;
import com.bernardomg.association.service.member.MemberFeeCalendarService;
import com.bernardomg.association.service.member.MemberService;
import com.bernardomg.association.service.member.MembershipBalanceService;

/**
 * Fee configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class FeeConfig {

    public FeeConfig() {
        super();
    }

    @Bean("feeCalendarService")
    public MemberFeeCalendarService getFeeCalendarService(final MemberFeeRepository memberFeeRepository,
            final MemberRepository memberRepository) {
        return new DefaultMemberFeeCalendarService(memberFeeRepository, memberRepository);
    }

    @Bean("feeMaintenanceScheduleTask")
    public FeeMaintenanceScheduleTask getFeeMaintenanceScheduleTask(final FeeMaintenanceService feeMaintenanceService) {
        return new FeeMaintenanceScheduleTask(feeMaintenanceService);
    }

    @Bean("feeMaintenanceService")
    public FeeMaintenanceService getFeeMaintenanceService(final FeeRepository feeRepo,
            final MemberRepository memberRepo) {
        return new DefaultFeeMaintenanceService(feeRepo, memberRepo);
    }

    @Bean("memberBalanceService")
    public MembershipBalanceService
            getMemberBalanceService(final MonthlyMemberBalanceRepository monthlyMemberBalanceRepository) {
        return new DefaultMembershipBalanceService(monthlyMemberBalanceRepository);
    }

    @Bean("memberService")
    public MemberService getMemberService(final MemberRepository memberRepository) {
        return new DefaultMemberService(memberRepository);
    }

}
