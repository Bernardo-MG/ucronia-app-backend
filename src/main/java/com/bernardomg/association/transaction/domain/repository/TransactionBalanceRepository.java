
package com.bernardomg.association.transaction.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.data.domain.Sorting;

public interface TransactionBalanceRepository {

    public Optional<TransactionCurrentBalance> findCurrent();

    public Collection<TransactionMonthlyBalance> findMonthlyBalance(final TransactionBalanceQuery query,
            final Sorting sorting);

}
