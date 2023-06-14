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
import com.bernardomg.association.transaction.model.request.TransactionQueryRequest;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - get all")
public class ITTransactionServiceGetAll {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities when reading a full year")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetAll_FullYear_Count() {
        final Iterable<Transaction>   result;
        final TransactionQueryRequest sample;
        final Pageable                pageable;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(12);
    }

    @Test
    @DisplayName("Returns all the entities data when reading a full year")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetAll_FullYear_Data() {
        final Iterator<Transaction>   result;
        final TransactionQueryRequest sample;
        final Pageable                pageable;
        Transaction                   data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

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

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 3");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 2, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 4");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 3, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 5");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 4, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 6");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 5, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 7");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 6, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 8");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 7, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 9");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 8, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 10");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 9, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getDescription())
            .isEqualTo("Transaction 11");
        Assertions.assertThat(data.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 10, 1).getTime());
        Assertions.assertThat(data.getAmount())
            .isEqualTo(1f);

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

    @Test
    @DisplayName("Returns all the entities when reading multiple entities")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_Multiple_Count() {
        final Iterable<Transaction>   result;
        final TransactionQueryRequest sample;
        final Pageable                pageable;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(5);
    }

    @Test
    @DisplayName("Returns all the entities data when reading multiple entities")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetAll_Multiple_Data() {
        final Iterator<Transaction>   result;
        final TransactionQueryRequest sample;
        final Pageable                pageable;
        Transaction                   data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

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
    @DisplayName("Returns a negative transaction")
    @Sql({ "/db/queries/transaction/negative.sql" })
    public void testGetAll_Negative() {
        final Iterator<Transaction>   result;
        final TransactionQueryRequest sample;
        final Pageable                pageable;
        Transaction                   data;

        pageable = Pageable.unpaged();

        sample = new DtoTransactionQueryRequest();

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
            .isEqualTo(-1);
    }

}
