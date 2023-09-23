
package com.bernardomg.association.membership.fee.model.request;

import java.time.YearMonth;

public interface FeeUpdate {

    public YearMonth getDate();

    public Long getMemberId();

    public Boolean getPaid();

}
