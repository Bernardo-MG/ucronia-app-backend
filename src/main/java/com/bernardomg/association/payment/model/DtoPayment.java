
package com.bernardomg.association.payment.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoPayment implements Payment {

    @NotNull
    private Integer     day;

    @NotEmpty
    @NotNull
    private String      description;

    private Long        id;

    @NotNull
    private Integer     month;

    @NotNull
    private Long        quantity;

    @NotNull
    private PaymentType type;

    @NotNull
    private Integer     year;

}
