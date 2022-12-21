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

package com.bernardomg.association.test.domain.transaction.integration.service;

import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.domain.transaction.model.DtoTransaction;
import com.bernardomg.association.domain.transaction.model.Transaction;
import com.bernardomg.association.domain.transaction.service.TransactionService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - get all - pagination")
@Sql({ "/db/queries/transaction/multiple.sql" })
public class ITTransactionServiceGetAllPagination {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Page_Container() {
        final Iterable<? extends Transaction> result;
        final Transaction                     sample;
        final Pageable                        pageable;

        pageable = Pageable.ofSize(10);

        sample = new DtoTransaction();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final Transaction                     sample;
        final Iterator<? extends Transaction> data;
        final Transaction                     result;
        final Pageable                        pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoTransaction();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Transaction 1", result.getDescription());
        Assertions.assertEquals(1, result.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), result.getDate()
            .toInstant());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final Transaction                     sample;
        final Iterator<? extends Transaction> data;
        final Transaction                     result;
        final Pageable                        pageable;

        pageable = PageRequest.of(1, 1);

        sample = new DtoTransaction();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Transaction 2", result.getDescription());
        Assertions.assertEquals(1, result.getAmount());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 2).toInstant(), result.getDate()
            .toInstant());
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final Iterable<? extends Transaction> result;
        final DtoTransaction                  sample;
        final Pageable                        pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoTransaction();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<? extends Transaction> result;
        final Transaction                     sample;
        final Pageable                        pageable;

        pageable = Pageable.unpaged();

        sample = new DtoTransaction();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

}
