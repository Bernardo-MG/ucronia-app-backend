
package com.bernardomg.association.fee.model.request;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Collection;

public interface FeesPayment {

    public Float getAmount();

    public String getDescription();

    public Collection<YearMonth> getFeeDates();

    public Long getMemberId();

    public LocalDateTime getPaymentDate();

}
