
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;

public interface FeeUpdateRequest {

    public Calendar getDate();

    public Long getId();

    public Long getMemberId();

    public Boolean getPaid();

}
