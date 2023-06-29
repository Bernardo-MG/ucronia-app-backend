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

package com.bernardomg.association.test.transaction.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.model.TransactionRange;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - get range")
public class ITTransactionServiceGetRange {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetRange() {
        super();
    }

    @Test
    @DisplayName("With a full year, a range for the full year is returned")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetRange_FullYear() {
        final TransactionRange range;

        range = service.getRange();

        Assertions.assertThat(range.getStartMonth())
            .isZero();
        Assertions.assertThat(range.getStartYear())
            .isEqualTo(2020);

        Assertions.assertThat(range.getEndMonth())
            .isEqualTo(11);
        Assertions.assertThat(range.getEndYear())
            .isEqualTo(2020);
    }

    @Test
    @DisplayName("With no data, an empty range is returned")
    public void testGetRange_NoData() {
        final TransactionRange range;

        range = service.getRange();

        Assertions.assertThat(range.getStartMonth())
            .isZero();
        Assertions.assertThat(range.getStartYear())
            .isZero();

        Assertions.assertThat(range.getEndMonth())
            .isZero();
        Assertions.assertThat(range.getEndYear())
            .isZero();
    }

}
