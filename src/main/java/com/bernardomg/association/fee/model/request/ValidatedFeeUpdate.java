
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;

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
    private Calendar date;

    @NotNull
    private Long     memberId;

    private String   name;

    @NotNull
    private Boolean  paid;

    private String   surname;

}
