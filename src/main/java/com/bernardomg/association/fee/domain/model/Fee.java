
package com.bernardomg.association.fee.domain.model;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Optional;

import com.bernardomg.association.person.domain.model.PersonName;

public record Fee(YearMonth month, Boolean paid, Member member, Optional<Transaction> transaction) {

    public static Fee unpaid(final YearMonth month, final Member person) {
        return new Fee(month, false, person, Optional.empty());
    }

    public static Fee paid(final YearMonth month, final Member person, final Transaction transaction) {
        return new Fee(month, true, person, Optional.of(transaction));
    }

    public static record Member(Long number, PersonName name) {}

    public static record Transaction(Instant date, Long index) {}

}
