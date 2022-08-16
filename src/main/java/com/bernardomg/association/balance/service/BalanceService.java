
package com.bernardomg.association.balance.service;

import com.bernardomg.association.balance.model.Balance;

public interface BalanceService {

    public Iterable<? extends Balance> getAll();

    public Balance getForMonth(final Integer month, final Integer year);

}
