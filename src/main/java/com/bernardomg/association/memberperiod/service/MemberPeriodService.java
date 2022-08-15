
package com.bernardomg.association.memberperiod.service;

import com.bernardomg.association.memberperiod.model.MemberPeriod;

public interface MemberPeriodService {

    public MemberPeriod create(final Long member, final MemberPeriod period);

    public Boolean delete(final Long id);

    public Iterable<? extends MemberPeriod> getAllForMember(final Long member);

    public MemberPeriod update(final Long member, final Long id, final MemberPeriod period);

}
