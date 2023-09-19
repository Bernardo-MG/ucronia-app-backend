
package com.bernardomg.association.funds.balance.service;

import java.util.Collection;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.funds.balance.model.Balance;
import com.bernardomg.association.funds.balance.model.BalanceQuery;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;

public interface BalanceService {

    public Collection<? extends MonthlyBalance> getMonthlyBalance(final BalanceQuery query, final Sort sort);

    public Balance getTotalBalance();

}
