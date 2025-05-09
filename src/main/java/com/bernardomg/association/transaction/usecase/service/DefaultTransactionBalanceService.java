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

package com.bernardomg.association.transaction.usecase.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.domain.repository.TransactionBalanceRepository;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the balance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class DefaultTransactionBalanceService implements TransactionBalanceService {

    private final TransactionBalanceRepository transactionBalanceRepository;

    public DefaultTransactionBalanceService(final TransactionBalanceRepository transactionBalanceRepo) {
        super();

        transactionBalanceRepository = Objects.requireNonNull(transactionBalanceRepo);
    }

    @Override
    public final TransactionCurrentBalance getBalance() {
        final Optional<TransactionCurrentBalance> readBalance;
        final TransactionCurrentBalance           currentBalance;

        // Find latest monthly balance
        readBalance = transactionBalanceRepository.findCurrent();

        if (readBalance.isEmpty()) {
            currentBalance = new TransactionCurrentBalance(0F, 0F);
        } else {
            currentBalance = readBalance.get();
        }

        return currentBalance;
    }

    @Override
    public final Collection<TransactionMonthlyBalance> getMonthlyBalance(final TransactionBalanceQuery query) {
        final Sorting sorting;

        sorting = new Sorting(List.of(Sorting.Property.asc("month")));
        return transactionBalanceRepository.findMonthlyBalance(query, sorting);
    }

}
