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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.repository.TransactionRepository;

@IntegrationTest
@DisplayName("Transaction repository - sum all")
public class TransactionRepositoryFindSumAll {

    @Autowired
    private TransactionRepository repository;

    public TransactionRepositoryFindSumAll() {
        super();
    }

    @Test
    @DisplayName("Returns the correct sum for a decimal transaction")
    @Sql({ "/db/queries/transaction/decimal.sql" })
    public void testFindSumAll_Decimal() {
        final Float result;

        result = repository.findSumAll();

        Assertions.assertEquals(Double.valueOf(0.12345)
            .floatValue(), result);
    }

    @Test
    @DisplayName("Returns the correct sum for multiple transactions")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testFindSumAll_Multiple() {
        final Float result;

        result = repository.findSumAll();

        Assertions.assertEquals(5, result);
    }

    @Test
    @DisplayName("Returns the correct sum for a negative transaction")
    @Sql({ "/db/queries/transaction/negative.sql" })
    public void testFindSumAll_Negative() {
        final Float result;

        result = repository.findSumAll();

        Assertions.assertEquals(-1, result);
    }

    @Test
    @DisplayName("Returns null when there is no data")
    public void testFindSumAll_NoData() {
        final Float result;

        result = repository.findSumAll();

        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("Returns the correct sum for a single transaction")
    @Sql({ "/db/queries/transaction/single.sql" })
    public void testFindSumAll_Single() {
        final Float result;

        result = repository.findSumAll();

        Assertions.assertEquals(1, result);
    }

    @Test
    @DisplayName("Returns the correct sum for a variety of values in the transaction")
    @Sql({ "/db/queries/transaction/variety.sql" })
    public void testFindSumAll_Variety() {
        final Float result;

        result = repository.findSumAll();

        Assertions.assertEquals(Double.valueOf(1.5)
            .floatValue(), result);
    }

}
