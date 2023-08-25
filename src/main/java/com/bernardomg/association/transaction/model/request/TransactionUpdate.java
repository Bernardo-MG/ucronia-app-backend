
package com.bernardomg.association.transaction.model.request;

import java.time.LocalDateTime;

public interface TransactionUpdate {

    public Float getAmount();

    public LocalDateTime getDate();

    public String getDescription();

}
