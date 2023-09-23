
package com.bernardomg.association.membership.fee.model.request;

import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedFeeUpdate implements FeeUpdate {

    @JsonFormat(pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @NotNull
    private YearMonth date;

    @NotNull
    private Long      memberId;

    private String    name;

    @NotNull
    private Boolean   paid;

    private String    surname;

}