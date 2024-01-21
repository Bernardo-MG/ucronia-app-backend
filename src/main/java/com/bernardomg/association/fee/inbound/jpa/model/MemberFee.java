
package com.bernardomg.association.fee.inbound.jpa.model;

import java.time.LocalDate;
import java.time.YearMonth;

public interface MemberFee {

    public YearMonth getDate();

    public Long getId();

    public Long getMemberId();

    public String getMemberName();

    public Long getMemberNumber();

    public Boolean getPaid();

    public LocalDate getPaymentDate();

    public Long getTransactionIndex();

}
