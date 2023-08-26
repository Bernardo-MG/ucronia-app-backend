
package com.bernardomg.association.fee.model;

import java.time.YearMonth;

public interface MemberFee {

    public YearMonth getDate();

    public Long getId();

    public Long getMemberId();

    public String getMemberName();

    public Boolean getPaid();

}
