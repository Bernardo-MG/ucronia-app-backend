
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

public record Fee(YearMonth date, Boolean paid, FeePerson person, FeeTransaction transaction) {

}
