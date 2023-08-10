
package com.bernardomg.association.fee.model;

import java.util.Calendar;

public interface MemberFee {

    public Calendar getDate();

    public Long getId();

    public Long getMemberId();

    public String getName();

    public Boolean getPaid();

}
