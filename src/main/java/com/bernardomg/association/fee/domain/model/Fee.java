
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import com.bernardomg.association.person.domain.model.PublicPerson;

public record Fee(YearMonth date, Boolean paid, PublicPerson person, FeeTransaction transaction) {

}
