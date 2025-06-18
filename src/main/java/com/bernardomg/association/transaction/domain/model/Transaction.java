
package com.bernardomg.association.transaction.domain.model;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

public record Transaction(long index, LocalDate date, float amount, String description) {

    public Transaction(final long index, final LocalDate date, final float amount, final String description) {
        this.amount = amount;
        this.date = date;
        this.description = StringUtils.trim(description);
        this.index = index;
    }

}
