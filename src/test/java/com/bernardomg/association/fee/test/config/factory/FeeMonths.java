
package com.bernardomg.association.fee.test.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.FeeCalendarMember;
import com.bernardomg.association.fee.domain.model.FeeCalendarMonth;
import com.bernardomg.association.fee.domain.model.FeeCalendarMonthFee;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class FeeMonths {

    public static final FeeCalendarMonth notPaid() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.CURRENT_DATE)
            .withPaid(false)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidCurrent() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.CURRENT_DATE)
            .withPaid(false)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidNextYear() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.NEXT_YEAR_DATE)
            .withPaid(false)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.NEXT_YEAR_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidPreviousMonth() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.PREVIOUS_MONTH_DATE)
            .withPaid(false)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth notPaidTwoMonthsBack() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.TWO_MONTHS_BACK_DATE)
            .withPaid(false)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paid() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.CURRENT_DATE)
            .withPaid(true)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paid(final int year, final int month) {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(YearMonth.of(year, month))
            .withPaid(true)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(month)
            .build();
    }

    public static final FeeCalendarMonth paidAtMonth(final int month) {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.CURRENT_DATE)
            .withPaid(true)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(month)
            .build();
    }

    public static final FeeCalendarMonth paidCurrent() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.CURRENT_DATE)
            .withPaid(true)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.CURRENT_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paidNextYear() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.NEXT_YEAR_DATE)
            .withPaid(true)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.NEXT_YEAR_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paidPreviousMonth() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.PREVIOUS_MONTH_DATE)
            .withPaid(true)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue())
            .build();
    }

    public static final FeeCalendarMonth paidTwoMonthsBack() {
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        fee = FeeCalendarMonthFee.builder()
            .withDate(MemberCalendars.TWO_MONTHS_BACK_DATE)
            .withPaid(true)
            .build();
        member = FeeCalendarMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return FeeCalendarMonth.builder()
            .withFee(fee)
            .withMember(member)
            .withMonth(MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue())
            .build();
    }

    private FeeMonths() {
        super();
    }

}
