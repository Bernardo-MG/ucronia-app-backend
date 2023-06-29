
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;

public interface FeeUpdate {

    public Calendar getDate();

    public Long getMemberId();

    public Boolean getPaid();

}
