
package com.bernardomg.association.paidmonth.service;

import com.bernardomg.association.paidmonth.model.PaidMonth;

public interface PaidMonthService {

    public PaidMonth create(final Long member, final PaidMonth month);

    public Boolean delete(final Long id);

    public Iterable<? extends PaidMonth> getAllForMember(final Long member);

    public PaidMonth update(final Long member, final PaidMonth month);

}
