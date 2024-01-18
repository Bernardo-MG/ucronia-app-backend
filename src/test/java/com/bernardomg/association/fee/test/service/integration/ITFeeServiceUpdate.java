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

package com.bernardomg.association.fee.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.model.FeeChange;
import com.bernardomg.association.fee.persistence.model.FeeEntity;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.FeeEntities;
import com.bernardomg.association.fee.test.config.factory.FeesUpdate;
import com.bernardomg.association.fee.test.util.assertion.FeeAssertions;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.test.data.fee.annotation.NotPaidFee;
import com.bernardomg.association.test.data.fee.annotation.PaidFee;
import com.bernardomg.association.test.data.member.annotation.ValidMember;
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
        final FeeChange change;

        // GIVEN
        change = FeesUpdate.nextMonth();

        // WHEN
        service.update(MemberConstants.NUMBER, FeeConstants.DATE, change);

        // THEN
        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a date change, the change is persisted")
    @ValidMember
    @NotPaidFee
    void testUpdate_Pay_PersistedData() {
        final FeeChange change;
        final FeeEntity fee;

        // GIVEN
        change = FeesUpdate.nextMonth();

        // WHEN
        service.update(MemberConstants.NUMBER, FeeConstants.DATE, change);
        fee = repository.findAll()
            .iterator()
            .next();

        // THEN
        FeeAssertions.isEqualTo(fee, FeeEntities.nextMonth());
    }

}
