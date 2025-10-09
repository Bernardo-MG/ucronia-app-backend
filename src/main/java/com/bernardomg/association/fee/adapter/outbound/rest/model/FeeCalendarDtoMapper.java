/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
