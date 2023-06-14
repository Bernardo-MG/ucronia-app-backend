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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.DtoTransactionQueryRequest;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - get all - filter")
public class ITTransactionServiceGetAllFilter {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities after a date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_AfterDate_Count() {
        final Iterable<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar(2020, 1, 2);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(4);
    }

    @Test
    @DisplayName("Returns all the entities data after a date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_AfterDate_Data() {
        final Iterator<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;
        Transaction                      data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar(2020, 1, 2);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 2).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 3");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 3).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 4");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 4).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 5");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 5).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Returns all the entities before a date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_BeforeDate_Count() {
        final Iterable<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar(2020, 1, 2);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(2);
    }

    @Test
    @DisplayName("Returns all the entities data before a date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_BeforeDate_Data() {
        final Iterator<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;
        Transaction                      data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar();
        date.set(2020, 1, 2);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable)
            .iterator();
        
        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 1");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 2).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Returns all the entities in a date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_InDate_Count() {
        final Iterable<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar(2020, 1, 2);
        sample.setDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns all the entities data in a date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_InDate_Data() {
        final Iterator<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;
        Transaction                      data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar(2020, 1, 2);
        sample.setDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 2).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Returns all the entities data for the first day of the year")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetAll_InDate_FirstDay_Data() {
        final Iterator<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;
        Transaction                      data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar(2020, 0, 1);
        sample.setDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 1");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 0, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Returns all the entities data for the last day of the year")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetAll_InDate_LastDay_Data() {
        final Iterator<Transaction>      result;
        final DtoTransactionQueryRequest sample;
        final Pageable                   pageable;
        final Calendar                   date;
        Transaction                      data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        date = new GregorianCalendar(2020, 11, 1);
        sample.setDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 12");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 11, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);
    }

}
