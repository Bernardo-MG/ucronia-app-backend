
package com.bernardomg.association.fee.model;

import java.util.Calendar;

public interface Fee {

    public Long getId();

    public String getMember();

    public Long getMemberId();

    public Boolean getPaid();

    public Calendar getPayDate();

}
