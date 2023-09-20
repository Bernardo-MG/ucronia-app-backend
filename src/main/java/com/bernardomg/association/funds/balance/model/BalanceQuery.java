
package com.bernardomg.association.funds.balance.model;

import java.time.YearMonth;

public interface BalanceQuery {

    public YearMonth getEndDate();

    public YearMonth getStartDate();

}