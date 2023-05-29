
package com.bernardomg.association.balance.service;

import java.util.Collection;

import com.bernardomg.association.balance.model.Balance;
import com.bernardomg.association.balance.model.MonthlyBalance;

public interface BalanceService {

    public Collection<MonthlyBalance> getMonthlyBalance();

    public Balance getTotalBalance();

}
