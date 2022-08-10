
package com.bernardomg.association.balance.model;

public interface Payment {

    public Integer getDay();

    public String getDescription();

    public Integer getMonth();

    public Long getQuantity();

    public PaymentType getType();

    public Integer getYear();

}
