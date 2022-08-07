
package com.bernardomg.association.member.service;

import com.bernardomg.association.member.model.PaidMonth;

public interface PaidMonthService {

    public PaidMonth create(final PaidMonth month);

    public Boolean delete(final Long id);

    public Iterable<? extends PaidMonth> getAll();

    public PaidMonth update(final PaidMonth month);

}
