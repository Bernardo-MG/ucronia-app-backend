
package com.bernardomg.association.membership.fee.model.request;

import java.time.YearMonth;

public interface FeeQuery {

    public YearMonth getDate();

    public YearMonth getEndDate();

    public YearMonth getStartDate();

}
