
package com.bernardomg.association.transaction.model.request;

import java.time.LocalDate;

public interface TransactionCreate {

    public Float getAmount();

    public LocalDate getDate();

    public String getDescription();

}
