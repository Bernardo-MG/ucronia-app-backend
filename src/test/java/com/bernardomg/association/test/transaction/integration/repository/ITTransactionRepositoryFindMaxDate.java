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

package com.bernardomg.association.test.transaction.integration.repository;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;

@IntegrationTest
@DisplayName("Transaction repository - max date")
public class ITTransactionRepositoryFindMaxDate {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositoryFindMaxDate() {
        super();
    }

    @Test
    @DisplayName("Returns the max date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testFindSumAll_Multiple() {
        final Calendar result;

        result = repository.findMaxDate();

        Assertions.assertEquals(new GregorianCalendar(2020, 1, 5, 0, 0, 0).getTime(), result.getTime());
    }

    @Test
    @DisplayName("Returns null when there is no data")
    public void testFindSumAll_NoData() {
        final Calendar result;

        result = repository.findMaxDate();

        Assertions.assertNull(result);
    }

}
