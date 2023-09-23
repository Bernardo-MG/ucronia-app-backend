
package com.bernardomg.association.membership.balance.model;

import java.time.YearMonth;

public interface MonthlyMemberBalance {

    public Long getDifference();

    public YearMonth getMonth();

    public Long getTotal();

}
