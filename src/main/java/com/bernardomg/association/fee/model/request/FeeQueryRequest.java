
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;

public interface FeeQueryRequest {

    public Calendar getDate();

    public Calendar getEndDate();

    public Calendar getStartDate();

}
