
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

public interface TransactionQueryRequest {

    public Calendar getDate();

    public Calendar getEndDate();

    public Calendar getStartDate();

}
