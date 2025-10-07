/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.transaction.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - find in range")
@MultipleTransactionsSameMonth
class ITTransactionRepositoryFindInRangeSort {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("With ascending order by date it returns the ordered data")
    void testFindInRange_Date_Asc() {
        final Collection<Transaction> transactions;
        final Sorting                 sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        transactions = repository.findInRange(TransactionConstants.START_DATE, TransactionConstants.END_DATE, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(5, Month.JANUARY));
    }

    @Test
    @DisplayName("With descending order by date it returns the ordered data")
    void testFindInRange_Date_Desc() {
        final Collection<Transaction> transactions;
        final Sorting                 sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.DESC)));

        // WHEN
        transactions = repository.findInRange(TransactionConstants.START_DATE, TransactionConstants.END_DATE, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndMonth(5, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(1, Month.JANUARY));
    }

}
