
package com.bernardomg.association.fee.model.request;

import java.time.YearMonth;

public interface FeeQuery {

    public YearMonth getDate();

    public YearMonth getEndDate();

    public YearMonth getStartDate();

}
