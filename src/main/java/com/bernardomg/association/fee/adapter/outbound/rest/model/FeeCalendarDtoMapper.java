
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.Year;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.ucronia.openapi.model.ContactDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarResponseDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarYearsRangeDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarYearsRangeResponseDto;
import com.bernardomg.ucronia.openapi.model.MembershipDto;

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
        final ContactDto     contact;
        final ContactNameDto contactName;
        final MembershipDto  membership;

        contactName = new ContactNameDto().firstName(feeCalendar.member()
            .name()
            .firstName())
            .lastName(feeCalendar.member()
                .name()
                .lastName())
            .fullName(feeCalendar.member()
                .name()
                .fullName());
        membership = new MembershipDto().active(feeCalendar.member()
            .membership()
            .active());
        contact = new ContactDto().name(contactName)
            .membership(membership)
            .number(feeCalendar.member()
                .number());
        return new FeeCalendarDto().year(feeCalendar.year())
            .member(contact);
    }

    private FeeCalendarDtoMapper() {
        super();
    }

}
