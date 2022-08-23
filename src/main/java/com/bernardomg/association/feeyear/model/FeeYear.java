
package com.bernardomg.association.feeyear.model;

public interface FeeYear {

    public String getMember();

    public Long getMemberId();

    public Iterable<FeeMonth> getMonths();

    public Integer getYear();

}
