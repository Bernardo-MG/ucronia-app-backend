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

package com.bernardomg.association.transaction.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionMonthsRange;
import com.bernardomg.association.transaction.persistence.model.TransactionEntity;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.persistence.specification.TransactionSpecifications;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultTransactionCalendarService implements TransactionCalendarService {

    private final TransactionRepository transactionRepository;

    public DefaultTransactionCalendarService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final TransactionMonthsRange getRange() {
        final Collection<YearMonth> months;

        log.debug("Reading the transactions range");

        months = transactionRepository.findMonths()
            .stream()
            .map(m -> YearMonth.of(m.getYear(), m.getMonth()))
            .toList();

        return TransactionMonthsRange.builder()
            .months(months)
            .build();
    }

    @Override
    public final Iterable<Transaction> getForMonth(final YearMonth date) {
        final Specification<TransactionEntity> spec;
        final Collection<TransactionEntity>    read;

        spec = TransactionSpecifications.on(date);
        read = transactionRepository.findAll(spec);

        return read.stream()
            .map(this::toDto)
            .toList();
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
