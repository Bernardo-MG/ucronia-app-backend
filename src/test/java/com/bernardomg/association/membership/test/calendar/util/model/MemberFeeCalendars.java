
package com.bernardomg.association.membership.test.calendar.util.model;

import java.time.YearMonth;
import java.util.List;

import com.bernardomg.association.membership.calendar.model.ImmutableFeeMonth;
import com.bernardomg.association.membership.calendar.model.ImmutableMemberFeeCalendar;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;

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

    public static final MemberFeeCalendar activeCurrentMonth() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(FeeInitializer.CURRENT_YEAR.getValue())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeNextYear() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(FeeInitializer.NEXT_YEAR.getValue())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activePreviousMonth() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(FeeInitializer.PREVIOUS_MONTH.getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar currentActive() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar currentInactive() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar fullCalendar() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .months(List.of(ImmutableFeeMonth.builder()
                .feeId(1L)
                .month(1)
                .paid(true)
                .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(2)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(3)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(4)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(5)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(6)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(7)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(8)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(9)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(10)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(11)
                    .paid(true)
                    .build(),
                ImmutableFeeMonth.builder()
                    .feeId(1L)
                    .month(12)
                    .paid(true)
                    .build()))
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

    public static final MemberFeeCalendar inactiveNextYear() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(FeeInitializer.NEXT_YEAR.getValue())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactivePreviousMonth() {
        return ImmutableMemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(FeeInitializer.PREVIOUS_MONTH.getYear())
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
