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

import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeMemberDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.FeeTransactionDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.MemberNameDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.MyFeeDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.MyFeePageResponseDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.fee.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.MyFee;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class MyFeeDtoMapper {

    public static final MyFeePageResponseDto toResponseDto(final Page<MyFee> page) {
        final SortingDto sorting;

        sorting = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(MyFeeDtoMapper::toDto)
            .toList());

        return new MyFeePageResponseDto().content(page.content()
            .stream()
            .map(MyFeeDtoMapper::toDto)
            .toList())
            .size(page.size())
            .page(page.page())
            .totalElements(page.totalElements())
            .totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage())
            .first(page.first())
            .last(page.last())
            .sort(sorting);
    }

    private static final FeeMemberDto toDto(final FeeMember member) {
        final MemberNameDto name;

        name = new MemberNameDto().firstName(member.name()
            .firstName())
            .lastName(member.name()
                .lastName())
            .nickname(member.name()
                .nickname()
                .orElse(null))
            .fullName(member.name()
                .fullName());

        return new FeeMemberDto().name(name)
            .number(member.number());
    }

    private static final MyFeeDto toDto(final MyFee fee) {
        return new MyFeeDto().month(fee.month())
            .paid(fee.paid())
            .member(toDto(fee.member()))
            .transaction(fee.transaction()
                .map(value -> new FeeTransactionDto().date(value.date())
                    .index(value.index()))
                .orElse(null));
    }

    private static final PropertyDto toDto(final Property property) {
        return new PropertyDto().name(property.name())
            .direction(property.direction() == Direction.ASC ? DirectionEnum.ASC : DirectionEnum.DESC);
    }

    private MyFeeDtoMapper() {
        super();
    }

}
