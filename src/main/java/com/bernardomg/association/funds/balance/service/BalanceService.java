
package com.bernardomg.association.funds.balance.service;

import java.util.Collection;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.model.request.BalanceQuery;

public interface BalanceService {

    public MonthlyBalance getBalance();

    public Collection<? extends MonthlyBalance> getMonthlyBalance(final BalanceQuery query, final Sort sort);

}
