
package com.bernardomg.association.transaction.domain.model;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record Transaction(float amount, LocalDate date, String description, long index) {

    public Transaction(final float amount, final LocalDate date, final String description, final long index) {
        this.amount = amount;
        this.date = date;
        this.description = StringUtils.trim(description);
        this.index = index;
    }

}
