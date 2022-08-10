
package com.bernardomg.association.member.service;

import com.bernardomg.association.member.model.MemberMonth;

public interface PaidMonthService {

    public MemberMonth create(final MemberMonth month);

    public Boolean delete(final Long id);

    public Iterable<? extends MemberMonth> getAll();

    public MemberMonth update(final MemberMonth month);

}
