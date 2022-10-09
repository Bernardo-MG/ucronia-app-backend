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

package com.bernardomg.association.test.crud.fee.integration.repository;

import java.util.GregorianCalendar;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.crud.fee.model.Fee;
import com.bernardomg.association.crud.fee.model.PersistentFee;
import com.bernardomg.association.crud.fee.repository.FeeRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee repository - find all with member - sort")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
public class ITFeeRepositoryFindAllWithMemberForYearSort {

    @Autowired
    private FeeRepository repository;

    public ITFeeRepositoryFindAllWithMemberForYearSort() {
        super();
    }

    @Test
    @DisplayName("Returns all data in descending order by member id")
    public void testGetAll_Sorted_Desc_MemberId() {
        final Iterator<? extends Fee> data;
        final Example<PersistentFee>  example;
        final Pageable                pageable;
        final Sort                    sort;
        Fee                           result;

        sort = Sort.by(Direction.DESC, "memberId");
        pageable = PageRequest.of(0, 10, sort);

        example = Example.of(new PersistentFee());

        data = repository.findAllWithMember(example, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(5, result.getMemberId());
        Assertions.assertEquals("Member 5 Surname", result.getMember());
        Assertions.assertEquals(new GregorianCalendar(2020, 5, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertFalse(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(4, result.getMemberId());
        Assertions.assertEquals("Member 4 Surname", result.getMember());
        Assertions.assertEquals(new GregorianCalendar(2020, 4, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(3, result.getMemberId());
        Assertions.assertEquals("Member 3 Surname", result.getMember());
        Assertions.assertEquals(new GregorianCalendar(2020, 3, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(2, result.getMemberId());
        Assertions.assertEquals("Member 2 Surname", result.getMember());
        Assertions.assertEquals(new GregorianCalendar(2020, 2, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1 Surname", result.getMember());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());
    }

}
