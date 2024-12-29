
package com.bernardomg.association.fee.domain.model;

import java.time.LocalDate;
import java.time.YearMonth;

import com.bernardomg.association.person.domain.model.PersonName;

public record Fee(YearMonth date, Boolean paid, Person person, Transaction transaction) {

    public static record Person(Long number, PersonName name) {

    }

    public static record Transaction(LocalDate date, Long index) {

    }
}
