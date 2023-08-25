
package com.bernardomg.association.transaction.model;

import java.time.LocalDateTime;

public interface Transaction {

    public Float getAmount();

    public LocalDateTime getDate();

    public String getDescription();

    public Long getId();

}
