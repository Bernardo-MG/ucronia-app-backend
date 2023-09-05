
package com.bernardomg.association.fee.model.request;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

public interface FeesPayment {

    public String getDescription();

    public Collection<YearMonth> getFeeDates();

    public Long getMemberId();

    public LocalDate getPaymentDate();

}
