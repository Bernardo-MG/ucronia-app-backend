
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record Fee(YearMonth date, Boolean paid, FeePerson person, FeeTransaction transaction) {

}
