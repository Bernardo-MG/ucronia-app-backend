
package com.bernardomg.association.member.service;

import com.bernardomg.association.member.model.MemberPeriod;

public interface MemberPeriodService {

    public MemberPeriod create(final Long member, final MemberPeriod period);

    public Boolean delete(final Long id);

    public Iterable<? extends MemberPeriod> getAll();

    public MemberPeriod update(final Long member, final MemberPeriod period);

}
