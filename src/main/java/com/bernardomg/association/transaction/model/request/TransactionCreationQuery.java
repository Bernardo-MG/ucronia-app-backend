
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

public interface TransactionCreationQuery {

    public Float getAmount();

    public Calendar getDate();

    public String getDescription();

}
