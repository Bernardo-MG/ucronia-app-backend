
package com.bernardomg.association.crud.transaction.model;

import java.util.Calendar;

public interface Transaction {

    public String getDescription();

    public Long getId();

    public Calendar getPayDate();

    public Float getAmount();

}
