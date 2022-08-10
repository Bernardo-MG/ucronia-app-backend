
package com.bernardomg.association.balance.model;

import lombok.Data;

@Data
public final class DtoPayment implements Payment {

    private Integer     day;

    private String      description = "";

    private Long        id          = -1L;

    private Integer     month;

    private Long        quantity    = -1L;

    private PaymentType type;

    private Integer     year;

}
