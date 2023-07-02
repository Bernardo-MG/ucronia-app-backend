/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.fee.service.integration;

import java.util.GregorianCalendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.test.fee.util.model.FeesUpdate;

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
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    void testUpdate_AddsNoEntity() {
        final FeeUpdate feeRequest;

        feeRequest = FeesUpdate.unpaid();

        service.update(1L, feeRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a not existing entity, a new entity is persisted")
    @Sql({ "/db/queries/member/single.sql" })
    void testUpdate_NotExisting_AddsEntity() {
        final FeeUpdate feeRequest;

        feeRequest = FeesUpdate.paid();

        service.update(10L, feeRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a value change on the paid flag, the change is persisted")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/unpaid.sql" })
    void testUpdate_Pay_PersistedData() {
        final FeeUpdate     feeRequest;
        final PersistentFee fee;

        feeRequest = FeesUpdate.paid();

        service.update(1L, feeRequest);
        fee = repository.findAll()
            .iterator()
            .next();

        FeeAssertions.isEqualTo(fee, PersistentFee.builder()
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    void testUpdate_PersistedData() {
        final FeeUpdate     feeRequest;
        final PersistentFee fee;

        feeRequest = FeesUpdate.unpaid();

        service.update(1L, feeRequest);
        fee = repository.findAll()
            .iterator()
            .next();

        FeeAssertions.isEqualTo(fee, PersistentFee.builder()
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
    void testUpdate_ReturnedData() {
        final FeeUpdate feeRequest;
        final MemberFee fee;

        feeRequest = FeesUpdate.unpaid();

        fee = service.update(1L, feeRequest);

        FeeAssertions.isEqualTo(fee, DtoMemberFee.builder()
            .memberId(1L)
            .name(null)
            .surname(null)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(false)
            .build());
    }

}
