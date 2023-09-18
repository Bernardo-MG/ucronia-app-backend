
package com.bernardomg.association.funds.balance.service;

import java.util.Collection;

import com.bernardomg.association.funds.balance.model.Balance;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;

public interface BalanceService {

    public Collection<? extends MonthlyBalance> getMonthlyBalance();

    public Balance getTotalBalance();

}
