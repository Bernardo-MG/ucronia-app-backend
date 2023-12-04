
package com.bernardomg.association.membership.test.calendar.util.model;

import com.bernardomg.association.membership.calendar.model.ImmutableMemberFeeCalendar;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;

public final class MemberFeeCalendars {

    public static final MemberFeeCalendar active() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeAlternative() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(2L)
            .memberName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar inactive() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactiveAlternative() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(2L)
            .memberName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar noSurname() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    private MemberFeeCalendars() {
        super();
    }

}
