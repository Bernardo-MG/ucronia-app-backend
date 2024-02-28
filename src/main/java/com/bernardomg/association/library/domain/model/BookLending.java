
package com.bernardomg.association.library.domain.model;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookLending {

    private String          isbn;

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth lendingDate;

    private long            member;

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth returnDate;
}
