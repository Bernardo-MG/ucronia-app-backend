
package com.bernardomg.association.fee.model;

import java.util.Calendar;

public interface FeeForm {

    public Long getId();

    public Long getMemberId();

    public Boolean getPaid();

    public Calendar getPayDate();

}
