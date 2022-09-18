
package com.bernardomg.association.status.feeyear.model;

public interface FeeYear {

    public Boolean getActive();

    public String getMember();

    public Long getMemberId();

    public Iterable<FeeMonth> getMonths();

    public Integer getYear();

}
