
package com.bernardomg.association.crud.fee.model;

import java.util.Calendar;

public interface FeeForm {

    public Long getId();

    public Long getMemberId();

    public Boolean getPaid();

    public Calendar getPayDate();

}
