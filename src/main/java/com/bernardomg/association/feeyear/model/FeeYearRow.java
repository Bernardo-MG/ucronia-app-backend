
package com.bernardomg.association.feeyear.model;

import java.util.Calendar;

public interface FeeYearRow {

    public Boolean getActive();

    public Long getId();

    public String getMember();

    public Long getMemberId();

    public Boolean getPaid();

    public Calendar getPayDate();

}
