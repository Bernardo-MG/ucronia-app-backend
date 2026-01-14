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
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.Fee.FeeType;
import com.bernardomg.association.fee.domain.model.Fee.Transaction;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.FeeCalendarMemberDto;
import com.bernardomg.ucronia.openapi.model.FeeChangeDto;
import com.bernardomg.ucronia.openapi.model.FeeDto;
import com.bernardomg.ucronia.openapi.model.FeeFeeTypeDto;
import com.bernardomg.ucronia.openapi.model.FeePageResponseDto;
import com.bernardomg.ucronia.openapi.model.FeePaymentsDto;
import com.bernardomg.ucronia.openapi.model.FeeResponseDto;
import com.bernardomg.ucronia.openapi.model.FeeTransactionDto;
import com.bernardomg.ucronia.openapi.model.FeesResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberFeeDto;
import com.bernardomg.ucronia.openapi.model.MemberFeesDto;
import com.bernardomg.ucronia.openapi.model.MemberFeesResponseDto;
import com.bernardomg.ucronia.openapi.model.MinimalProfileDto;
import com.bernardomg.ucronia.openapi.model.ProfileNameDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;
import com.bernardomg.ucronia.openapi.model.YearsRangeDto;
import com.bernardomg.ucronia.openapi.model.YearsRangeResponseDto;

public final class FeeDtoMapper {

    public static final Fee toDomain(final FeeChangeDto change, final YearMonth month, final long number) {
        final Transaction transaction;
        final Fee         fee;
        final FeeType     feeType;

        feeType = new Fee.FeeType(change.getFeeType(), "", 0f);
        if ((change.getTransaction()
            .getIndex() == null)
                && ((change.getTransaction()
                    .getDate() == null))) {
            fee = Fee.unpaid(month, number, null, feeType);
        } else {
            transaction = new Fee.Transaction(change.getTransaction()
                .getDate(),
                change.getTransaction()
                    .getIndex());
            fee = Fee.paid(month, number, null, feeType, transaction);
        }

        return fee;
    }

    public static final FeePayments toDomain(final FeePaymentsDto dto) {
        return new FeePayments(dto.getMember(), dto.getPaymentDate(), dto.getMonths());
    }

    public static final MemberFeesResponseDto toMemberResponseDto(final Collection<MemberFees> fees) {
        return new MemberFeesResponseDto().content(fees.stream()
            .map(FeeDtoMapper::toDto)
            .toList());
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
        final ProfileNameDto    name;
        final MinimalProfileDto member;
        final FeeTransactionDto transaction;
        final FeeFeeTypeDto     feeType;

        name = new ProfileNameDto().firstName(fee.member()
            .name()
            .firstName())
            .lastName(fee.member()
                .name()
                .lastName())
            .fullName(fee.member()
                .name()
                .fullName());

        member = new MinimalProfileDto().name(name)
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

        return new FeeDto().month(fee.month())
            .paid(fee.paid())
            .member(member)
            .transaction(transaction)
            .feeType(feeType);
    }

    private static final MemberFeesDto toDto(final MemberFees memberFee) {
        final FeeCalendarMemberDto member;
        final ProfileNameDto       name;
        final List<MemberFeeDto>   months;

        name = new ProfileNameDto().firstName(memberFee.member()
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
        return new MemberFeesDto().member(member)
            .fees(months);
    }

    private static final MemberFeeDto toDto(final MemberFees.Fee fee) {
        return new MemberFeeDto().month(fee.month())
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

    private FeeDtoMapper() {
        super();
    }

}
