
package com.bernardomg.association.transaction.domain.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;

public interface TransactionBalanceRepository {

    public Optional<TransactionCurrentBalance> findCurrent();

    public Collection<TransactionMonthlyBalance> findMonthlyBalance(final TransactionBalanceQuery query,
            final Sort sort);

}
