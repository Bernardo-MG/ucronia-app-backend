
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

public interface TransactionQuery {

    public Calendar getDate();

    public Calendar getEndDate();

    public Calendar getStartDate();

}
