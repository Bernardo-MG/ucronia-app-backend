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

package com.bernardomg.association.transaction.usecase;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.infra.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.infra.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.infra.jpa.specification.TransactionSpecifications;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultTransactionCalendarService implements TransactionCalendarService {

    private final TransactionSpringRepository transactionRepository;

    public DefaultTransactionCalendarService(final TransactionSpringRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final TransactionCalendarMonth getForMonth(final YearMonth date) {
        final Specification<TransactionEntity> spec;
        final Collection<TransactionEntity>    read;
        final Collection<Transaction>          transactions;

        spec = TransactionSpecifications.on(date);
        read = transactionRepository.findAll(spec);

        transactions = read.stream()
            .map(this::toDto)
            .toList();
        return TransactionCalendarMonth.builder()
            .date(date)
            .transactions(transactions)
            .build();
    }

    @Override
    public final TransactionCalendarMonthsRange getRange() {
        final Collection<YearMonth> months;

        log.debug("Reading the transactions range");

        months = transactionRepository.findMonths()
            .stream()
            .map(m -> YearMonth.of(m.getYear(), m.getMonth()))
            .toList();

        return TransactionCalendarMonthsRange.builder()
            .months(months)
            .build();
    }

    private final Transaction toDto(final TransactionEntity entity) {
        return Transaction.builder()
            .index(entity.getIndex())
            .date(entity.getDate())
            .description(entity.getDescription())
            .amount(entity.getAmount())
            .build();
    }

}
