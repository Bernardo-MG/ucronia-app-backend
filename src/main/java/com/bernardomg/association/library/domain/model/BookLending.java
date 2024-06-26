
package com.bernardomg.association.library.domain.model;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookLending {

    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth lendingDate;

    private long      member;

    private long      number;

    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth returnDate;
}
