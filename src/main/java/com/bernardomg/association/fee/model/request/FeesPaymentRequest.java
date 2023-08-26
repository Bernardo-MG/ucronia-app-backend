
package com.bernardomg.association.fee.model.request;

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
public final class FeesPaymentRequest implements FeesPayment {

    @NotNull
    private Float                          amount;

    @NotEmpty
    private String                         description;

    @DateTimeFormat(pattern = "yyyy-MM")
    @NotEmpty
    private Collection<@NotNull YearMonth> feeDates;

    @NotNull
    private Long                           memberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate                      paymentDate;

}
