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

package com.bernardomg.association.test.transaction.repository.integration;

import java.time.LocalDateTime;
import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction repository - max date")
class ITTransactionRepositoryFindMaxDate {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositoryFindMaxDate() {
        super();
    }

    @Test
    @DisplayName("With multiple transactions, it returns the max date")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    void testFindSumAll_Multiple() {
        final LocalDateTime calendar;

        calendar = repository.findMaxDate();

        Assertions.assertThat(calendar)
            .isEqualTo(LocalDateTime.of(2020, Month.JANUARY, 5, 0, 0));
    }

    @Test
    @DisplayName("Withno transactions, no max date is returned")
    void testFindSumAll_NoData() {
        final LocalDateTime calendar;

        calendar = repository.findMaxDate();

        Assertions.assertThat(calendar)
            .isNull();
    }

}
