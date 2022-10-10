
package com.bernardomg.association.status.feeyear.model;

import java.util.Calendar;

public interface FeeYearRow {

    public Boolean getActive();

    public Calendar getDate();

    public Long getId();

    public Long getMemberId();

    public String getName();

    public Boolean getPaid();

    public String getSurname();

}
