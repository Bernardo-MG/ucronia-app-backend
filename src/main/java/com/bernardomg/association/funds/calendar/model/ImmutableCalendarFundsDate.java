
package com.bernardomg.association.funds.calendar.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ImmutableCalendarFundsDate implements CalendarFundsDate {

    private final Float     amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final String    description;

    private final Long      id;

}
