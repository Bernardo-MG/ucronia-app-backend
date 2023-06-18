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

package com.bernardomg.association.test.balance.integration.service;

import java.util.GregorianCalendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.balance.model.Balance;
import com.bernardomg.association.balance.service.BalanceService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;

@IntegrationTest
@DisplayName("Balance service - get total balance")
public class ITBalanceServiceGetTotalBalance {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private BalanceService        service;

    public ITBalanceServiceGetTotalBalance() {
        super();
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("Reads values around zero")
    public void testGetTotalBalance_AroundZero(final Float amount) {
        final Balance               result;
        final PersistentTransaction entity;

        entity = PersistentTransaction.builder()
            .date(new GregorianCalendar(2020, 0, 1))
            .description("Description")
            .amount(amount)
            .build();

        repository.save(entity);

        result = service.getTotalBalance();

        Assertions.assertThat(result.getAmount())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("Returns the expected balance when there are multiple transactions")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetTotalBalance_Multiple() {
        final Balance result;

        result = service.getTotalBalance();

        Assertions.assertThat(result.getAmount())
            .isEqualTo(5);
    }

}
