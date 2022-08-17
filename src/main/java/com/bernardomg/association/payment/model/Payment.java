
package com.bernardomg.association.payment.model;

public interface Payment {

    public Integer getDay();

    public String getDescription();

    public Long getId();

    public Integer getMonth();

    public Long getQuantity();

    public PaymentType getType();

    public Integer getYear();

}
