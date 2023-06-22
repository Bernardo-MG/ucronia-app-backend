
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;

public interface FeeCreateRequest {

    public Calendar getDate();

    public Long getMemberId();

    public Boolean getPaid();

}
