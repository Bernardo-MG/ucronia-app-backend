
package com.bernardomg.association.fee.adapter.inbound.jpa.model;

import java.time.LocalDate;
import java.time.YearMonth;

public interface MemberFee {

    public YearMonth getDate();

    public Long getId();

    public Boolean getPaid();

    public LocalDate getPaymentDate();

    public String getPersonFirstName();

    public Long getPersonId();

    public String getPersonLastName();

    public Long getPersonNumber();

    public Long getTransactionIndex();

}
