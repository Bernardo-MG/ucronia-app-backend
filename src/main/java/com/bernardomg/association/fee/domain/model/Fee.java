
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import com.bernardomg.association.person.domain.model.PersonName;

public record Fee(YearMonth date, Boolean paid, Person person, FeeTransaction transaction) {

    public static record Person(Long number, PersonName name) {

    }

}
