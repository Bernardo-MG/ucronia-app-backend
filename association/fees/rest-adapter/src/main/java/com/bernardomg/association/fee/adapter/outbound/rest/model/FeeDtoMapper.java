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

import java.time.Instant;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeCalendarDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeCalendarMemberDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeCalendarResponseDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeFeeTypeDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeePageResponseDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeePaymentsDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeResponseDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeTransactionDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeUpdateDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeesResponseDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.MemberDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.MemberNameDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.MonthFeeDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.YearsRangeDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.YearsRangeResponseDto;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.Fee.Transaction;
import com.bernardomg.association.fee.domain.model.FeePayments;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class FeeDtoMapper {

    public static final FeeCalendarResponseDto toCalendarResponseDto(final Collection<MemberFees> fees) {
        return new FeeCalendarResponseDto().content(fees.stream()
            .map(FeeDtoMapper::toDto)
            .toList());
    }

    public static final FeePayments toDomain(final FeePaymentsDto dto) {
        final List<YearMonth> months;

        months = dto.getMonths()
            .stream()
            .map(YearMonth::from)
            .toList();

        return new FeePayments(dto.getMember(), dto.getPaymentDate(), months);
    }

    public static final Fee toDomain(final FeeUpdateDto change, final YearMonth month, final long number) {
        final Transaction transaction;
        final Fee         fee;

        if (change.getTransaction() == null) {
            fee = Fee.unpaid(month, number, null, null);
        } else {
            transaction = new Fee.Transaction(null, change.getTransaction());
            fee = Fee.paid(month, number, null, null, transaction);
        }

        return fee;
    }

    public static final FeesResponseDto toResponseDto(final Collection<Fee> fees) {
        return new FeesResponseDto().content(fees.stream()
            .map(FeeDtoMapper::toDto)
            .toList());
    }

    public static final FeeResponseDto toResponseDto(final Fee fee) {
        return new FeeResponseDto().content(toDto(fee));
    }

    public static final FeeResponseDto toResponseDto(final Optional<Fee> fee) {
        return new FeeResponseDto().content(fee.map(FeeDtoMapper::toDto)
            .orElse(null));
    }

    public static final FeePageResponseDto toResponseDto(final Page<Fee> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(FeeDtoMapper::toDto)
            .toList());
        return new FeePageResponseDto().content(page.content()
            .stream()
            .map(FeeDtoMapper::toDto)
            .toList())
            .size(page.size())
            .page(page.page())
            .totalElements(page.totalElements())
            .totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage())
            .first(page.first())
            .last(page.last())
            .sort(sortingResponse);
    }

    public static final YearsRangeResponseDto toResponseDto(final YearsRange range) {
        final List<Integer> years;

        years = range.years()
            .stream()
            .map(Year::getValue)
            .toList();
        return new YearsRangeResponseDto().content(new YearsRangeDto().years(years));
    }

    private static final FeeDto toDto(final Fee fee) {
        final MemberNameDto     name;
        final MemberDto         member;
        final FeeTransactionDto transaction;
        final FeeFeeTypeDto     feeType;

        name = new MemberNameDto().firstName(fee.member()
            .name()
            .firstName())
            .lastName(fee.member()
                .name()
                .lastName())
            .fullName(fee.member()
                .name()
                .fullName());

        member = new MemberDto().name(name)
            .number(fee.member()
                .number());

        if (fee.transaction()
            .isPresent()) {
            transaction = new FeeTransactionDto().date(fee.transaction()
                .get()
                .date())
                .index(fee.transaction()
                    .get()
                    .index());
        } else {
            transaction = null;
        }

        feeType = new FeeFeeTypeDto();
        feeType.number(fee.feeType()
            .number());
        feeType.name(fee.feeType()
            .name());
        feeType.amount(fee.feeType()
            .amount());

        return new FeeDto().month(toInstant(fee.month()))
            .paid(fee.paid())
            .member(member)
            .transaction(transaction)
            .feeType(feeType);
    }

    private static final FeeCalendarDto toDto(final MemberFees memberFee) {
        final FeeCalendarMemberDto member;
        final MemberNameDto        name;
        final List<MonthFeeDto>    months;

        name = new MemberNameDto().firstName(memberFee.member()
            .name()
            .firstName())
            .lastName(memberFee.member()
                .name()
                .lastName())
            .fullName(memberFee.member()
                .name()
                .fullName());
        member = new FeeCalendarMemberDto().name(name)
            .number(memberFee.member()
                .number())
            .active(memberFee.member()
                .active());
        months = memberFee.fees()
            .stream()
            .map(FeeDtoMapper::toDto)
            .toList();
        return new FeeCalendarDto().member(member)
            .fees(months);
    }

    private static final MonthFeeDto toDto(final MemberFees.Fee fee) {
        return new MonthFeeDto().month(toInstant(fee.month()))
            .paid(fee.paid());
    }

    private static final PropertyDto toDto(final Property property) {
        final DirectionEnum direction;

        if (property.direction() == Direction.ASC) {
            direction = DirectionEnum.ASC;
        } else {
            direction = DirectionEnum.DESC;
        }
        return new PropertyDto().name(property.name())
            .direction(direction);
    }

    private static final Instant toInstant(final YearMonth month) {
        return month.atDay(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant();
    }

    private FeeDtoMapper() {
        super();
    }

}
