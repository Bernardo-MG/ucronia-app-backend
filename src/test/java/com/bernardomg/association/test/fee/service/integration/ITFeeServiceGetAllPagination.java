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
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeeQuery;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.test.fee.util.model.FeesQuery;

@IntegrationTest
@DisplayName("Fee service - get all - pagination")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
class ITFeeServiceGetAllPagination {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("With an active pagination, the returned data is contained in a page")
    void testGetAll_Page_Container() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.ofSize(10);

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    void testGetAll_Page1() {
        final FeeQuery            feeQuery;
        final Iterable<MemberFee> fees;
        final Iterator<MemberFee> feesItr;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 1);

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    void testGetAll_Page2() {
        final FeeQuery            feeQuery;
        final Iterable<MemberFee> fees;
        final Iterator<MemberFee> feesItr;
        final Pageable            pageable;

        pageable = PageRequest.of(1, 1);

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With an inactive pagination, the returned data is contained in a page")
    void testGetAll_Unpaged_Container() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .isInstanceOf(Page.class);
    }

}
