
package com.bernardomg.association.funds.calendar.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.calendar.model.CalendarFundsDate;
import com.bernardomg.association.funds.calendar.model.ImmutableCalendarFundsDate;
import com.bernardomg.association.funds.calendar.model.ImmutableTransactionRange;
import com.bernardomg.association.funds.calendar.model.TransactionRange;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionSpecifications;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultFundsCalendarService implements FundsCalendarService {

    private final TransactionRepository transactionRepository;

    public DefaultFundsCalendarService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final TransactionRange getRange() {
        final Collection<YearMonth> months;

        log.debug("Reading the transactions range");

        months = transactionRepository.findMonths()
            .stream()
            .map(m -> YearMonth.of(m.getYear(), m.getMonth()))
            .toList();

        return ImmutableTransactionRange.builder()
            .months(months)
            .build();
    }

    @Override
    public final Iterable<? extends CalendarFundsDate> getYearMonth(final YearMonth date) {
        final Specification<PersistentTransaction> spec;
        final Collection<PersistentTransaction>    read;

        spec = TransactionSpecifications.fromDate(date);
        read = transactionRepository.findAll(spec);

        return read.stream()
            .map(this::toDto)
            .toList();
    }

    private final CalendarFundsDate toDto(final PersistentTransaction entity) {
        return ImmutableCalendarFundsDate.builder()
            .id(entity.getId())
            .date(entity.getDate())
            .description(entity.getDescription())
            .amount(entity.getAmount())
            .build();
    }

}
