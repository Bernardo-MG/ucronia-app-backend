
package com.bernardomg.association.feeyear.model;

import java.util.Calendar;

public interface FeeYearRow {

    public Long getId();

    public String getMember();
    public Boolean getActive();

    public Long getMemberId();

    public Boolean getPaid();

    public Calendar getPayDate();

}
