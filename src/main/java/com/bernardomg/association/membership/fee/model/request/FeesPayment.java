
package com.bernardomg.association.membership.fee.model.request;

import java.time.YearMonth;
import java.util.Collection;

public interface FeesPayment {

    public Collection<YearMonth> getFeeDates();

    public Long getMemberId();

}
