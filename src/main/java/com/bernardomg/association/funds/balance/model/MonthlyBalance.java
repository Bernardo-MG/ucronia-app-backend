
package com.bernardomg.association.funds.balance.model;

import java.time.YearMonth;

public interface MonthlyBalance {

    public Float getDifference();

    public YearMonth getMonth();

    public Float getTotal();

}
