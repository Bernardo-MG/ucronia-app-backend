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
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.ImmutableMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.DtoFeeQueryRequest;
import com.bernardomg.association.fee.model.request.FeeQueryRequest;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.assertion.FeeAssertions;

@IntegrationTest
@DisplayName("Fee service - get all - inactive member")
@Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/single.sql" })
public class ITFeeServiceGetAllInactiveMember {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllInactiveMember() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<MemberFee> result;
        final FeeQueryRequest     sample;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterator<MemberFee> data;
        final FeeQueryRequest     sample;
        final MemberFee           result;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        FeeAssertions.isEqualTo(result, ImmutableMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

}
