
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

public final record FeeQuery(YearMonth date, YearMonth startDate, YearMonth endDate) {

}
