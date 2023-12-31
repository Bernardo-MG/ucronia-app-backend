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

package com.bernardomg.association.membership.test.fee.service.integration;

import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.membership.fee.model.FeeUpdate;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.test.fee.config.NotPaidFee;
import com.bernardomg.association.membership.test.fee.config.PaidFee;
import com.bernardomg.association.membership.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.membership.test.fee.util.model.FeeEntities;
import com.bernardomg.association.membership.test.fee.util.model.Fees;
import com.bernardomg.association.membership.test.fee.util.model.FeesUpdate;
import com.bernardomg.association.membership.test.fee.util.model.MemberFees;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - update")
class ITFeeServiceUpdate {

    @Autowired
    private FeeRepository repository;

    @Autowired
    private FeeService    service;

    public ITFeeServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With an existing entity, no new entity is persisted")
    @ValidMember
    @PaidFee
    void testUpdate_AddsNoEntity() {
        final FeeUpdate feeRequest;

        // GIVEN
        feeRequest = FeesUpdate.notPaid();

        // WHEN
        service.update(1L, Fees.DATE, feeRequest);

        // THEN
        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a value change on the paid flag, the change is persisted")
    @ValidMember
    @NotPaidFee
    void testUpdate_Pay_PersistedData() {
        final FeeUpdate feeRequest;
        final FeeEntity fee;

        // GIVEN
        feeRequest = FeesUpdate.paid();

        // WHEN
        service.update(1L, Fees.DATE, feeRequest);
        fee = repository.findAll()
            .iterator()
            .next();

        // THEN
        FeeAssertions.isEqualTo(fee, FeeEntities.paidAt(Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    @ValidMember
    @PaidFee
    void testUpdate_PersistedData() {
        final FeeUpdate feeRequest;
        final FeeEntity fee;

        // GIVEN
        feeRequest = FeesUpdate.notPaid();

        // WHEN
        service.update(1L, Fees.DATE, feeRequest);
        fee = repository.findAll()
            .iterator()
            .next();

        // THEN
        FeeAssertions.isEqualTo(fee, FeeEntities.notPaidAt(Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    @ValidMember
    @PaidFee
    void testUpdate_ReturnedData() {
        final FeeUpdate feeRequest;
        final MemberFee fee;

        // GIVEN
        feeRequest = FeesUpdate.notPaid();

        // WHEN
        fee = service.update(1L, Fees.DATE, feeRequest);

        // THEN
        Assertions.assertThat(fee)
            .isEqualTo(MemberFees.notPaid());
    }

}
