
package com.bernardomg.association.funds.balance.model.request;

import java.time.YearMonth;

public interface BalanceQuery {

    public YearMonth getEndDate();

    public YearMonth getStartDate();

}
