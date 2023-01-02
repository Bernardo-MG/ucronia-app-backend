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

package com.bernardomg.association.test.transaction.integration.service;

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

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.model.DtoTransactionRequest;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - get all - filter")
@Sql({ "/db/queries/transaction/multiple.sql" })
public class ITTransactionServiceGetAllFilter {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities after a date")
    public void testGetAll_AfterDate_Count() {
        final Iterable<? extends Transaction> result;
        final DtoTransactionRequest           sample;
        final Pageable                        pageable;
        final Calendar                        date;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionRequest();

        date = new GregorianCalendar(2020, 1, 2, 0, 0, 0);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(4, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data after a date")
    public void testGetAll_AfterDate_Data() {
        final Iterator<? extends Transaction> result;
        final DtoTransactionRequest           sample;
        final Pageable                        pageable;
        final Calendar                        date;
        Transaction                           data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionRequest();

        date = new GregorianCalendar(2020, 1, 2, 0, 0, 0);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 2", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 2, 0, 0, 0).toInstant(), data.getDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 3", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 3, 0, 0, 0).toInstant(), data.getDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 4", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 4, 0, 0, 0).toInstant(), data.getDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 5", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 5, 0, 0, 0).toInstant(), data.getDate()
            .toInstant());
    }

    @Test
    @DisplayName("Returns all the entities before a date")
    public void testGetAll_BeforeDate_Count() {
        final Iterable<? extends Transaction> result;
        final DtoTransactionRequest           sample;
        final Pageable                        pageable;
        final Calendar                        date;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionRequest();

        date = new GregorianCalendar(2020, 1, 2, 0, 0, 0);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(2, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data before a date")
    public void testGetAll_BeforeDate_Data() {
        final Iterator<? extends Transaction> result;
        final DtoTransactionRequest           sample;
        final Pageable                        pageable;
        final Calendar                        date;
        Transaction                           data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionRequest();

        date = new GregorianCalendar();
        date.set(2020, 1, 2, 0, 0, 0);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 1", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1, 0, 0, 0).toInstant(), data.getDate()
            .toInstant());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 2", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 2, 0, 0, 0).toInstant(), data.getDate()
            .toInstant());
    }

    @Test
    @DisplayName("Returns all the entities in a date")
    public void testGetAll_InDate_Count() {
        final Iterable<? extends Transaction> result;
        final DtoTransactionRequest           sample;
        final Pageable                        pageable;
        final Calendar                        date;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionRequest();

        date = new GregorianCalendar(2020, 1, 2, 0, 0, 0);
        sample.setDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data after a date")
    public void testGetAll_InDate_Data() {
        final Iterator<? extends Transaction> result;
        final DtoTransactionRequest           sample;
        final Pageable                        pageable;
        final Calendar                        date;
        Transaction                           data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionRequest();

        date = new GregorianCalendar(2020, 1, 2, 0, 0, 0);
        sample.setDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Transaction 2", data.getDescription());
        Assertions.assertEquals(1, data.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 2, 0, 0, 0).toInstant(), data.getDate()
            .toInstant());
    }

}
