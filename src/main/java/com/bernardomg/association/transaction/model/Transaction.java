
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

public interface Transaction {

    public Float getAmount();

    public Calendar getDate();

    public String getDescription();

    public Long getId();

}
