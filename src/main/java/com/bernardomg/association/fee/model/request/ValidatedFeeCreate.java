
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class ValidatedFeeCreate implements FeeCreate {

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
