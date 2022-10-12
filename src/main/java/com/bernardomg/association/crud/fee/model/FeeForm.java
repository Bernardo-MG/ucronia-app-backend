
package com.bernardomg.association.crud.fee.model;

import java.util.Calendar;

public interface FeeForm {

    public Calendar getDate();

    public Long getId();

    public Long getMemberId();

    public Boolean getPaid();

}
