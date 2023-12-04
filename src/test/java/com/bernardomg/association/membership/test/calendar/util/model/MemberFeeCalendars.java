
package com.bernardomg.association.membership.test.calendar.util.model;

import com.bernardomg.association.membership.calendar.model.ImmutableMemberFeeCalendar;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;

public final class MemberFeeCalendars {

    public static final MemberFeeCalendar active() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .year(2020)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeAlternative() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(2L)
            .memberName("Member 2 Surname 2")
            .year(2020)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar inactive() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .year(2020)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactiveAlternative() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(2L)
            .memberName("Member 2 Surname 2")
            .year(2020)
            .active(false)
            .build();
    }

    private MemberFeeCalendars() {
        super();
    }

}
