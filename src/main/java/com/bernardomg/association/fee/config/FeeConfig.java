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

package com.bernardomg.association.fee.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.ActiveMemberSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeePaymentSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaActiveMemberRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaAssignedFeeActiveMemberRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.MemberFeeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.schedule.FeeMaintenanceScheduleTask;
import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.usecase.service.DefaultFeeCalendarService;
import com.bernardomg.association.fee.usecase.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.fee.usecase.service.DefaultFeeReportService;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.fee.usecase.service.DefaultUserFeeService;
import com.bernardomg.association.fee.usecase.service.FeeCalendarService;
import com.bernardomg.association.fee.usecase.service.FeeMaintenanceService;
import com.bernardomg.association.fee.usecase.service.FeeReportService;
import com.bernardomg.association.fee.usecase.service.FeeService;
import com.bernardomg.association.fee.usecase.service.UserFeeService;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;

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

    @Bean("activeMemberRepository")
    public ActiveMemberRepository getActiveMemberRepository(final ActiveMemberSpringRepository activeMemberRepo) {
        return new JpaActiveMemberRepository(activeMemberRepo);
    }

    @Bean("assignedFeeActiveMemberSource")
    public MemberRepository getActiveMemberSource(final ActiveMemberSpringRepository activeMemberRepo,
            final MemberSpringRepository memberSpringRepo, final PersonSpringRepository personSpringRepository) {
        return new JpaAssignedFeeActiveMemberRepository(activeMemberRepo, memberSpringRepo, personSpringRepository);
    }

    @Bean("feeCalendarService")
    public FeeCalendarService getFeeCalendarService(final FeeRepository feeRepo,
            final ActiveMemberRepository activeMemberRepository) {
        return new DefaultFeeCalendarService(feeRepo, activeMemberRepository);
    }

    @Bean("feeMaintenanceScheduleTask")
    public FeeMaintenanceScheduleTask getFeeMaintenanceScheduleTask(final FeeMaintenanceService feeMaintenanceService) {
        return new FeeMaintenanceScheduleTask(feeMaintenanceService);
    }

    @Bean("feeMaintenanceService")
    public FeeMaintenanceService getFeeMaintenanceService(final FeeRepository feeRepo,
            final ActiveMemberRepository activeMemberRepository, final MemberRepository memberRepository) {
        return new DefaultFeeMaintenanceService(feeRepo, activeMemberRepository, memberRepository);
    }

    @Bean("FeeReportService")
    public FeeReportService getFeeReportService(final FeeRepository feeRepo) {
        return new DefaultFeeReportService(feeRepo);
    }

    @Bean("feeRepository")
    public FeeRepository getFeeRepository(final FeeSpringRepository feeSpringRepo,
            final MemberFeeSpringRepository memberFeeSpringRepo, final PersonSpringRepository personSpringRepo,
            final ActiveMemberSpringRepository activeMemberSpringRepo,
            final FeePaymentSpringRepository feePaymentSpringRepo,
            final TransactionSpringRepository transactionSpringRepo) {
        return new JpaFeeRepository(feeSpringRepo, memberFeeSpringRepo, personSpringRepo, activeMemberSpringRepo,
            feePaymentSpringRepo, transactionSpringRepo);
    }

    @Bean("feeService")
    public FeeService getFeeService(final FeeRepository feeRepo, final PersonRepository personRepo,
            final MemberRepository memberRepo, final TransactionRepository transactionRepo,
            final AssociationConfigurationSource configSource, final MessageSource msgSource) {
        return new DefaultFeeService(feeRepo, personRepo, memberRepo, transactionRepo, configSource, msgSource);
    }

    @Bean("userFeeService")
    public UserFeeService getUserFeeService(final FeeRepository feeRepository,
            final UserPersonRepository userPersonRepository) {
        return new DefaultUserFeeService(feeRepository, userPersonRepository);
    }

}
