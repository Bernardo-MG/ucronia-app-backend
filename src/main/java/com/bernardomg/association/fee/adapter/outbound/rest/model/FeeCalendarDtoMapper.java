
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.Year;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.MemberFees.Fee;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeCalendarMemberDto;
import com.bernardomg.ucronia.openapi.model.MemberFeeDto;
import com.bernardomg.ucronia.openapi.model.MemberFeesDto;
import com.bernardomg.ucronia.openapi.model.MemberFeesResponseDto;
import com.bernardomg.ucronia.openapi.model.YearsRangeDto;
import com.bernardomg.ucronia.openapi.model.YearsRangeResponseDto;

public final class FeeCalendarDtoMapper {

    public static final MemberFeesResponseDto toResponseDto(final Collection<MemberFees> fees) {
        return new MemberFeesResponseDto().content(fees.stream()
            .map(FeeCalendarDtoMapper::toDto)
            .toList());
    }

    public static final YearsRangeResponseDto toResponseDto(final YearsRange range) {
        final List<Integer> years;

        years = range.years()
            .stream()
            .map(Year::getValue)
            .toList();
        return new YearsRangeResponseDto().content(new YearsRangeDto().years(years));
    }

    private static final MemberFeeDto toDto(final Fee month) {
        return new MemberFeeDto().month(month.month())
            .paid(month.paid());
    }

    private static final MemberFeesDto toDto(final MemberFees memberFee) {
        final FeeCalendarMemberDto member;
        final ContactNameDto       contactName;
        final List<MemberFeeDto>   months;

        contactName = new ContactNameDto().firstName(memberFee.member()
            .name()
            .firstName())
            .lastName(memberFee.member()
                .name()
                .lastName())
            .fullName(memberFee.member()
                .name()
                .fullName());
        member = new FeeCalendarMemberDto().name(contactName)
            .number(memberFee.member()
                .number())
            .active(memberFee.member()
                .active());
        months = memberFee.fees()
            .stream()
            .map(FeeCalendarDtoMapper::toDto)
            .toList();
        return new MemberFeesDto().member(member)
            .fees(months);
    }

    private FeeCalendarDtoMapper() {
        super();
    }

}
