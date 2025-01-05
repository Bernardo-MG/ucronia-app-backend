
package com.bernardomg.association.fee.domain.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import com.bernardomg.association.person.domain.model.PersonName;

public record Fee(YearMonth date, Boolean paid, Person person, Optional<Transaction> transaction) {
    
    public static Fee unpaid(YearMonth date, Person person) {
        return new Fee(date, false, person, Optional.empty());
    }

    public static record Person(Long number, PersonName name) {

    }

    public static record Transaction(LocalDate date, Long index) {

    }
}
