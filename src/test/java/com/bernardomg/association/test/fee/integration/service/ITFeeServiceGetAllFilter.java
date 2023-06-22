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

package com.bernardomg.association.test.fee.integration.service;

import java.util.GregorianCalendar;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.ImmutableMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeeQuery;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.test.fee.util.model.FeesQuery;

@IntegrationTest
@DisplayName("Fee service - get all - filter")
public class ITFeeServiceGetAllFilter {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("With a filter applied to the end date, the returned data is filtered")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_EndDate() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(new GregorianCalendar(2020, 1, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);
        FeeAssertions.isEqualTo(IterableUtils.first(fees), ImmutableMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With a filter applied to the end date which covers no fee, no data is returned")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_EndDate_NotInRange() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(new GregorianCalendar(2020, 0, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isZero();
    }

    @Test
    @DisplayName("With a filter applied to the date, the returned data is filtered")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_InDate() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(new GregorianCalendar(2020, 2, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);
        FeeAssertions.isEqualTo(IterableUtils.first(fees), ImmutableMemberFee.builder()
            .memberId(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With a filter applied to the date using the lowest date, the returned data is filtered")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_InDate_FirstDay_Data() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(new GregorianCalendar(2020, 0, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);
        FeeAssertions.isEqualTo(IterableUtils.first(fees), ImmutableMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 0, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With a filter applied to the date using the highest date, the returned data is filtered")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_InDate_LastDay_Data() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(new GregorianCalendar(2020, 11, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);
        FeeAssertions.isEqualTo(IterableUtils.first(fees), ImmutableMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 11, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With a filter applied to the date using a not existing date, no data is returned")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_InDate_NotExisting() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(new GregorianCalendar(2020, 10, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isZero();
    }

    @Test
    @DisplayName("With a filter applied to the start date, the returned data is filtered")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_StartDate() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.startDate(new GregorianCalendar(2020, 5, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);
        FeeAssertions.isEqualTo(IterableUtils.first(fees), ImmutableMemberFee.builder()
            .memberId(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("With a filter applied to the start date which covers no fee, no data is returned")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_StartDate_NotInRange() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.startDate(new GregorianCalendar(2020, 6, 1));

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isZero();
    }

}
