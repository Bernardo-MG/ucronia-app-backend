
package com.bernardomg.association.balance.model;

import java.time.LocalDate;

public interface MonthlyBalance {

    public Float getCumulative();

    public LocalDate getDate();

    public Float getTotal();

}
