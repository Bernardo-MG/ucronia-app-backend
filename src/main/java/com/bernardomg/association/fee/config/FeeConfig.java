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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.fee.schedule.FeeMaintenanceScheduleTask;
import com.bernardomg.association.fee.service.DefaultFeeCalendarService;
import com.bernardomg.association.fee.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.fee.service.FeeCalendarService;
import com.bernardomg.association.fee.service.FeeMaintenanceService;
import com.bernardomg.association.member.persistence.repository.ActiveMemberRepository;

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
    public FeeCalendarService getFeeCalendarService(final MemberFeeRepository memberFeeRepository,
            final ActiveMemberRepository activeMemberRepository) {
        return new DefaultFeeCalendarService(memberFeeRepository, activeMemberRepository);
    }

    @Bean("feeMaintenanceScheduleTask")
    public FeeMaintenanceScheduleTask getFeeMaintenanceScheduleTask(final FeeMaintenanceService feeMaintenanceService) {
        return new FeeMaintenanceScheduleTask(feeMaintenanceService);
    }

    @Bean("feeMaintenanceService")
    public FeeMaintenanceService getFeeMaintenanceService(final FeeRepository feeRepo,
            final ActiveMemberRepository activeMemberRepository) {
        return new DefaultFeeMaintenanceService(feeRepo, activeMemberRepository);
    }

}
