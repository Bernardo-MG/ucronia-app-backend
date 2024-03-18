
package com.bernardomg.association.library.domain.model;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookLending {

    private long      index;

    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth lendingDate;

    private long      member;

    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth returnDate;
}
