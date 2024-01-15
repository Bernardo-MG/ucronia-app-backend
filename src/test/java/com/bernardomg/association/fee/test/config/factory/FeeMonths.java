
package com.bernardomg.association.fee.test.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.model.FeeCalendarMember;
import com.bernardomg.association.fee.model.FeeCalendarMonth;
import com.bernardomg.association.fee.model.FeeCalendarMonthFee;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class FeeMonths {

    public static final FeeCalendarMonth notPaid() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .paid(false)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidCurrent() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .paid(false)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidNextYear() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.NEXT_YEAR_DATE)
            .paid(false)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.NEXT_YEAR_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidPreviousMonth() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.PREVIOUS_MONTH_DATE)
            .paid(false)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidTwoMonthsBack() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.TWO_MONTHS_BACK_DATE)
            .paid(false)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paid() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .paid(true)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paid(final int year, final int month) {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(YearMonth.of(year, month))
            .paid(true)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(month)
            .build();
    }

    public static final FeeCalendarMonth paidAtMonth(final int month) {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .paid(true)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(month)
            .build();
    }

    public static final FeeCalendarMonth paidCurrent() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .paid(true)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paidNextYear() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.NEXT_YEAR_DATE)
            .paid(true)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.NEXT_YEAR_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paidPreviousMonth() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.PREVIOUS_MONTH_DATE)
            .paid(true)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paidTwoMonthsBack() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .date(MemberCalendars.TWO_MONTHS_BACK_DATE)
            .paid(true)
            .build();
        member = FeeCalendarMember.builder()
            .number(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue())
            .build();
    }

    private FeeMonths() {
        super();
    }

}
