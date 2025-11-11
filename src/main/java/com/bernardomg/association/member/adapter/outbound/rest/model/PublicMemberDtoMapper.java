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

package com.bernardomg.association.member.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.PublicMemberDto;
import com.bernardomg.ucronia.openapi.model.PublicMemberPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PublicMemberResponseDto;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class PublicMemberDtoMapper {

    public static final PublicMemberResponseDto toResponseDto(final Optional<PublicMember> member) {
        return new PublicMemberResponseDto().content(member.map(PublicMemberDtoMapper::toDto)
            .orElse(null));
    }

    public static final PublicMemberPageResponseDto toResponseDto(final Page<PublicMember> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(PublicMemberDtoMapper::toDto)
            .toList());
        return new PublicMemberPageResponseDto().content(page.content()
            .stream()
            .map(PublicMemberDtoMapper::toDto)
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

    private static final PublicMemberDto toDto(final PublicMember member) {
        final ContactNameDto contactName;

        contactName = new ContactNameDto().firstName(member.name()
            .firstName())
            .lastName(member.name()
                .lastName())
            .fullName(member.name()
                .fullName());
        return new PublicMemberDto().number(member.number())
            .name(contactName);
    }

    private PublicMemberDtoMapper() {
        super();
    }

}
