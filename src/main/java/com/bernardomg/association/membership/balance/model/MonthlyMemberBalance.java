
package com.bernardomg.association.membership.balance.model;

import java.time.LocalDate;

public interface MonthlyMemberBalance {

    public Long getDifference();

    public LocalDate getMonth();

    public Long getTotal();

}
