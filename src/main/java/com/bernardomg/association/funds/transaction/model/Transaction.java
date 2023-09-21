
package com.bernardomg.association.funds.transaction.model;

import java.time.LocalDate;

public interface Transaction {

    public Float getAmount();

    public LocalDate getDate();

    public String getDescription();

    public Long getId();

}
