
package com.bernardomg.association.membership.member.model.request;

import java.time.YearMonth;

public interface MemberBalanceQuery {

    public YearMonth getEndDate();

    public YearMonth getStartDate();

}
