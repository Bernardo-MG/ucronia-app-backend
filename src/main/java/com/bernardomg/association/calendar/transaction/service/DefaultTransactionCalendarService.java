
package com.bernardomg.association.calendar.transaction.service;

import java.time.YearMonth;
import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bernardomg.association.calendar.transaction.model.ImmutableTransactionRange;
import com.bernardomg.association.calendar.transaction.model.TransactionRange;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.mapper.TransactionMapper;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.persistence.repository.TransactionSpecifications;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultTransactionCalendarService implements TransactionCalendarService {

    private final TransactionMapper     mapper;

    private final TransactionRepository repository;

    @Override
    public final TransactionRange getRange() {
        final Collection<YearMonth> months;

        log.debug("Reading the transactions range");

        months = repository.findMonths()
            .stream()
            .map(m -> YearMonth.of(m.getYear(), m.getMonth()))
            .toList();

        return ImmutableTransactionRange.builder()
            .months(months)
            .build();
    }

    @Override
    public final Iterable<? extends Transaction> getYearMonth(final YearMonth date) {
        final Specification<PersistentTransaction> spec;
        final Collection<PersistentTransaction>    read;

        spec = TransactionSpecifications.fromDate(date);
        read = repository.findAll(spec);

        return read.stream()
            .map(mapper::toDto)
            .toList();
    }

}
