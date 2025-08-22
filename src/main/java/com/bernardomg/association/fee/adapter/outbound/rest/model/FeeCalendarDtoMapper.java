
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.Year;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendar.FeeCalendarMonth;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarMonthDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarResponseDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarYearsRangeDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarYearsRangeResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberDto;

public final class FeeCalendarDtoMapper {

    public static final FeeCalendarResponseDto toResponseDto(final Collection<FeeCalendar> calendars) {
        return new FeeCalendarResponseDto().content(calendars.stream()
            .map(FeeCalendarDtoMapper::toDto)
            .toList());
    }

    public static final FeeCalendarYearsRangeResponseDto toResponseDto(final FeeCalendarYearsRange range) {
        final List<Integer> years;

        years = range.years()
            .stream()
            .map(Year::getValue)
            .toList();
        return new FeeCalendarYearsRangeResponseDto().content(new FeeCalendarYearsRangeDto().years(years));
    }

    private static final FeeCalendarDto toDto(final FeeCalendar feeCalendar) {
        final MemberDto                 member;
        final ContactNameDto            contactName;
        final List<FeeCalendarMonthDto> months;

        contactName = new ContactNameDto().firstName(feeCalendar.member()
            .name()
            .firstName())
            .lastName(feeCalendar.member()
                .name()
                .lastName())
            .fullName(feeCalendar.member()
                .name()
                .fullName());
        member = new MemberDto().name(contactName)
            .number(feeCalendar.member()
                .number())
            .active(feeCalendar.member()
                .active());
        months = feeCalendar.months()
            .stream()
            .map(FeeCalendarDtoMapper::toDto)
            .toList();
        return new FeeCalendarDto().year(feeCalendar.year())
            .member(member)
            .months(months);
    }

    private static final FeeCalendarMonthDto toDto(final FeeCalendarMonth month) {
        return new FeeCalendarMonthDto().month(month.month());
    }

    private FeeCalendarDtoMapper() {
        super();
    }

}
