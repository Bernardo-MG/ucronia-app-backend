
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

public interface TransactionUpdate {

    public Float getAmount();

    public Calendar getDate();

    public String getDescription();

    public Long getId();

}
