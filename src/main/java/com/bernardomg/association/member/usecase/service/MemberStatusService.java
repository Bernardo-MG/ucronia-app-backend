
package com.bernardomg.association.member.usecase.service;

import java.time.YearMonth;

public interface MemberStatusService {

    public void activate(final YearMonth date, final Long personNumber);

}
