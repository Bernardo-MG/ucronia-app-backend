
package com.bernardomg.association.funds.transaction.model.request;

import java.time.LocalDate;

public interface TransactionQuery {

    public LocalDate getDate();

    public LocalDate getEndDate();

    public LocalDate getStartDate();

}
