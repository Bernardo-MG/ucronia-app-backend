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
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.ActiveMemberSpringRepository;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.AssignedFeeActiveMemberRepository;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.FeePaymentSpringRepository;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.JpaFeeRepository;
import com.bernardomg.association.fee.infra.inbound.jpa.repository.MemberFeeSpringRepository;
import com.bernardomg.association.fee.infra.outbound.schedule.FeeMaintenanceScheduleTask;
import com.bernardomg.association.fee.usecase.service.DefaultFeeCalendarService;
import com.bernardomg.association.fee.usecase.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.fee.usecase.service.FeeCalendarService;
import com.bernardomg.association.fee.usecase.service.FeeMaintenanceService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.infra.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.transaction.infra.inbound.jpa.repository.TransactionSpringRepository;

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

    @Bean("assignedFeeActiveMemberSource")
    public MemberRepository getActiveMemberSource(final ActiveMemberSpringRepository activeMemberRepo,
            final MemberSpringRepository memberSpringRepo) {
        return new AssignedFeeActiveMemberRepository(activeMemberRepo, memberSpringRepo);
    }

    @Bean("feeCalendarService")
    public FeeCalendarService getFeeCalendarService(final MemberFeeSpringRepository memberFeeRepository,
            final ActiveMemberSpringRepository activeMemberRepository) {
        return new DefaultFeeCalendarService(memberFeeRepository, activeMemberRepository);
    }

    @Bean("feeMaintenanceScheduleTask")
    public FeeMaintenanceScheduleTask getFeeMaintenanceScheduleTask(final FeeMaintenanceService feeMaintenanceService) {
        return new FeeMaintenanceScheduleTask(feeMaintenanceService);
    }

    @Bean("feeMaintenanceService")
    public FeeMaintenanceService getFeeMaintenanceService(final FeeSpringRepository feeRepo,
            final ActiveMemberSpringRepository activeMemberRepository) {
        return new DefaultFeeMaintenanceService(feeRepo, activeMemberRepository);
    }

    @Bean("feeRepository")
    public FeeRepository getFeeRepository(final FeeSpringRepository feeRepo,
            final MemberFeeSpringRepository memberFeeRepo, final MemberSpringRepository memberRepo,
            final FeePaymentSpringRepository feePaymentRepo, final TransactionSpringRepository transactionRepo,
            final AssociationConfigurationSource configurationSrc, final MessageSource messageSrc) {
        return new JpaFeeRepository(feeRepo, memberFeeRepo, memberRepo, feePaymentRepo, transactionRepo,
            configurationSrc, messageSrc);
    }

}
