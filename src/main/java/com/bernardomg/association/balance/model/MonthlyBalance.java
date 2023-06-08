
package com.bernardomg.association.balance.model;

import java.util.Calendar;

public interface MonthlyBalance {

    public Float getCumulative();

    public Calendar getDate();

    public Float getTotal();

}
