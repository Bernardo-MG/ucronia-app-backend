
package com.bernardomg.association.fee.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FeePayment {

    @DateTimeFormat(pattern = "yyyy-MM")
    @NotEmpty
    private Collection<@NotNull YearMonth> feeDates;

    @NotNull
    private Long                           memberNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate                      paymentDate;

}