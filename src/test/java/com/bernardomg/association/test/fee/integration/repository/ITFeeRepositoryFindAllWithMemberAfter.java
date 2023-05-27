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

package com.bernardomg.association.test.fee.integration.repository;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.repository.FeeRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee repository - find all with member by date")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
public class ITFeeRepositoryFindAllWithMemberAfter {

    @Autowired
    private FeeRepository repository;

    public ITFeeRepositoryFindAllWithMemberAfter() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testFindAllWithMember_Count() {
        final Iterable<MemberFee> result;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        date = new GregorianCalendar(2020, 5, 1);

        result = repository.findAllWithMemberAfter(date, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testFindAllWithMember_Data() {
        final Iterator<MemberFee> data;
        MemberFee                 result;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        date = new GregorianCalendar(2020, 5, 1);

        data = repository.findAllWithMemberAfter(date, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(5, result.getMemberId());
        Assertions.assertEquals("Member 5", result.getName());
        Assertions.assertEquals("Surname 5", result.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 5, 1).getTime(), result.getDate()
            .getTime());
        Assertions.assertFalse(result.getPaid());
    }

    @Test
    @DisplayName("Returns no data when the date doesn't exist")
    public void testFindAllWithMember_NotExisting() {
        final Iterable<MemberFee> result;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        date = new GregorianCalendar(2020, 10, 1);

        result = repository.findAllWithMemberAfter(date, pageable);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

}
