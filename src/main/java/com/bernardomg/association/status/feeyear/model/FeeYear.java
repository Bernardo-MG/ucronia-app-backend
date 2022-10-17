
package com.bernardomg.association.status.feeyear.model;

public interface FeeYear {

    public Boolean getActive();

    public Long getMemberId();

    public Iterable<FeeMonth> getMonths();

    public String getName();

    public String getSurname();

    public Integer getYear();

}
