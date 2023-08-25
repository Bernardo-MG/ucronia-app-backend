
package com.bernardomg.association.transaction.model.request;

import java.time.LocalDateTime;

public interface TransactionQuery {

    public LocalDateTime getDate();

    public LocalDateTime getEndDate();

    public LocalDateTime getStartDate();

}
