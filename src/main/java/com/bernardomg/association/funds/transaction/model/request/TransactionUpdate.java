
package com.bernardomg.association.funds.transaction.model.request;

import java.time.LocalDate;

public interface TransactionUpdate {

    public Float getAmount();

    public LocalDate getDate();

    public String getDescription();

}
