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

package com.bernardomg.association.test.crud.transaction.integration.service;

import java.util.GregorianCalendar;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.crud.transaction.model.DtoTransaction;
import com.bernardomg.association.crud.transaction.model.Transaction;
import com.bernardomg.association.crud.transaction.service.DefaultTransactionService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default transaction service - get all - sort")
@Sql({ "/db/queries/transaction/multiple.sql" })
public class ITDefaultTransactionServiceGetAllSorted {

    @Autowired
    private DefaultTransactionService service;

    public ITDefaultTransactionServiceGetAllSorted() {
        super();
    }

    @Test
    @DisplayName("Returns all data in ascending order by id")
    public void testGetAll_Sorted_Asc_Id() {
        final Iterator<? extends Transaction> result;
        final Transaction                     sample;
        Transaction                           data;
        final Pageable                        pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "id");

        sample = new DtoTransaction();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 1", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 2", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 3", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 4", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 5", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());
    }

    @Test
    @DisplayName("Returns all data in descending order by id")
    public void testGetAll_Sorted_Desc_Id() {
        final Iterator<? extends Transaction> result;
        final Transaction                     sample;
        Transaction                           data;
        final Pageable                        pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "id");

        sample = new DtoTransaction();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 5", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 4", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 3", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 2", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 1", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getPayDate()
            .toInstant());
    }

}
