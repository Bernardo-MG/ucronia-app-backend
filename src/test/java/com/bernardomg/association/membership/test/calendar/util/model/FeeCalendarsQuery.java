
package com.bernardomg.association.membership.test.calendar.util.model;

import com.bernardomg.association.membership.calendar.model.request.DtoFeeCalendarQueryRequest;
import com.bernardomg.association.membership.member.model.MemberStatus;

public final class FeeCalendarsQuery {

    public static final DtoFeeCalendarQueryRequest active() {
        return DtoFeeCalendarQueryRequest.builder()
            .status(MemberStatus.ACTIVE)
            .build();
    }

    private FeeCalendarsQuery() {
        super();
    }

}