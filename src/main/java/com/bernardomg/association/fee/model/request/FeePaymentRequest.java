
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public final class FeePaymentRequest implements FeePayment {

    @NotNull
    private Float                amount;

    @NotEmpty
    private String               description;

    @JsonFormat(pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @NotEmpty
    private Collection<@NotNull Calendar> feeDates;

    @NotNull
    private Long                 memberId;

    @JsonFormat(pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    @NotNull
    private Calendar             paymentDate;

}
