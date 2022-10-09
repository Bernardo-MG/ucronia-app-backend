
package com.bernardomg.association.status.feeyear.model;

import java.util.Calendar;

public interface FeeYearRow {

    public Boolean getActive();

    public Calendar getDate();

    public Long getId();

    public String getMember();

    public Long getMemberId();

    public Boolean getPaid();

}
