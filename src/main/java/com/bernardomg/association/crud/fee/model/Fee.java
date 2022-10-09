
package com.bernardomg.association.crud.fee.model;

import java.util.Calendar;

public interface Fee {

    public Calendar getDate();

    public Long getId();

    public String getMember();

    public Long getMemberId();

    public Boolean getPaid();

}
