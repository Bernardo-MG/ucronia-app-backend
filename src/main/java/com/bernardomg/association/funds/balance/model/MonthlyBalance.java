
package com.bernardomg.association.funds.balance.model;

import java.time.LocalDate;

public interface MonthlyBalance {

    public Float getDifference();

    public LocalDate getMonth();

    public Float getTotal();

}
