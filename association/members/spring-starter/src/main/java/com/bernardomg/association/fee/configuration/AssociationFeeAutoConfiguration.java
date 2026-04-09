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

package com.bernardomg.association.fee.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bernardomg.association.fee.adapter.inbound.event.RegisterFeesOnMonthStartEventListener;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.FeeTypeSpringRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeMemberRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeSummaryRepository;
import com.bernardomg.association.fee.adapter.inbound.jpa.repository.JpaFeeTypeRepository;
import com.bernardomg.association.fee.domain.repository.FeeMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeSummaryRepository;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.usecase.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.fee.usecase.service.DefaultFeeSummaryService;
import com.bernardomg.association.fee.usecase.service.DefaultFeeTypeService;
import com.bernardomg.association.fee.usecase.service.FeeMaintenanceService;
import com.bernardomg.association.fee.usecase.service.FeeService;
import com.bernardomg.association.fee.usecase.service.FeeSummaryService;
import com.bernardomg.association.fee.usecase.service.FeeTypeService;
import com.bernardomg.association.fee.usecase.service.MyFeesService;
import com.bernardomg.association.fee.usecase.service.SpringSecurityMyFeesService;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.event.emitter.EventEmitter;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.association.fee.adapter.outbound.rest.controller",
        "com.bernardomg.association.fee.adapter.inbound.jpa" })
public class AssociationFeeAutoConfiguration {

    @Bean("feeMaintenanceService")
    public FeeMaintenanceService getFeeMaintenanceService(final FeeRepository feeRepository,
            final FeeMemberRepository feeMemberRepository) {
        return new DefaultFeeMaintenanceService(feeRepository, feeMemberRepository);
    }

    @Bean("feeMemberRepository")
    public FeeMemberRepository
            getFeeMemberRepository(final MemberProfileSpringRepository memberProfileSpringRepository) {
        return new JpaFeeMemberRepository(memberProfileSpringRepository);
    }

    @Bean("feeRepository")
    public FeeRepository getFeeRepository(final FeeSpringRepository feeSpringRepository,
            final ProfileSpringRepository profileSpringRepository,
            final FeeTypeSpringRepository feeTypeSpringRepository,
            final TransactionSpringRepository transactionSpringRepository) {
        return new JpaFeeRepository(feeSpringRepository, profileSpringRepository, feeTypeSpringRepository,
            transactionSpringRepository);
    }

    @Bean("feeService")
    public FeeService getFeeService(final FeeRepository feeRepository, final FeeMemberRepository feeMemberRepository,
            final TransactionRepository transactionRepository, final EventEmitter evntEmitter,
            final MessageSource msgSource) {
        return new DefaultFeeService(feeRepository, feeMemberRepository, transactionRepository, evntEmitter, msgSource);
    }

    @Bean("feeSummaryRepository")
    public FeeSummaryRepository getFeeSummaryRepository(final FeeSpringRepository feeSpringRepository) {
        return new JpaFeeSummaryRepository(feeSpringRepository);
    }

    @Bean("feeSummaryService")
    public FeeSummaryService getFeeSummaryService(final FeeSummaryRepository feeSummaryRepository) {
        return new DefaultFeeSummaryService(feeSummaryRepository);
    }

    @Bean("feeTypeRepository")
    public FeeTypeRepository getFeeTypeRepository(final FeeTypeSpringRepository feeTypeSpringRepository) {
        return new JpaFeeTypeRepository(feeTypeSpringRepository);
    }

    @Bean("feeTypeService")
    public FeeTypeService getFeeTypeService(final FeeTypeRepository feeTypeRepository) {
        return new DefaultFeeTypeService(feeTypeRepository);
    }

    @Bean("myFeesService")
    public MyFeesService getMyFeesService(final FeeRepository feeRepository,
            final UserProfileRepository userProfileRepository) {
        return new SpringSecurityMyFeesService(feeRepository, userProfileRepository);
    }

    @Bean("registerFeesOnMonthStartEventListener")
    public RegisterFeesOnMonthStartEventListener
            getRegisterFeesOnMonthStartEventListener(final FeeMaintenanceService service) {
        return new RegisterFeesOnMonthStartEventListener(service);
    }

}
