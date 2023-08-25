
package com.bernardomg.association.balance.model;

import java.time.LocalDateTime;

public interface MonthlyBalance {

    public Float getCumulative();

    public LocalDateTime getDate();

    public Float getTotal();

}
