
package com.bernardomg.association.person.usecase.service;

import java.time.YearMonth;

public interface MemberStatusService {

    public void activate(final YearMonth date, final Long personNumber);

    public void activateRenewed();

    public void deactivate(final YearMonth date, final Long personNumber);

    public void deactivateNotRenewed();

}
