
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;
import java.util.Collection;

public interface FeeCreate {

    public Float getAmount();

    public String getDescription();

    public Collection<Calendar> getFeeDates();

    public Long getMemberId();

    public Calendar getPaymentDate();

}
