
package com.bernardomg.association.transaction.domain.model;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;

public record Transaction(long index, Instant date, float amount, String description) {

    public Transaction(final long index, final Instant date, final float amount, final String description) {
        this.amount = amount;
        this.date = date;
        this.description = StringUtils.trim(description);
        this.index = index;
    }

}
