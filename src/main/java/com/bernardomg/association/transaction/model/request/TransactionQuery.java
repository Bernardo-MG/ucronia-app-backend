
package com.bernardomg.association.transaction.model.request;

import java.time.LocalDate;

public interface TransactionQuery {

    public LocalDate getDate();

    public LocalDate getEndDate();

    public LocalDate getStartDate();

}
