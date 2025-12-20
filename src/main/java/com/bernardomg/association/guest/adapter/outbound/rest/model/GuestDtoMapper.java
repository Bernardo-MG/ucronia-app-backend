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

package com.bernardomg.association.guest.adapter.outbound.rest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.GuestChangeDto;
import com.bernardomg.ucronia.openapi.model.GuestCreationDto;
import com.bernardomg.ucronia.openapi.model.GuestDto;
import com.bernardomg.ucronia.openapi.model.GuestPageResponseDto;
import com.bernardomg.ucronia.openapi.model.GuestResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class GuestDtoMapper {

    public static final Guest toDomain(final GuestCreationDto creation) {
        final ContactName name;

        name = new ContactName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new Guest(-1L, name, List.of());
    }

    public static final Guest toDomain(final long number, final GuestChangeDto change) {
        return new Guest(number, null, new ArrayList<>(change.getGames()));
    }

    public static final GuestResponseDto toResponseDto(final Guest contact) {
        return new GuestResponseDto().content(GuestDtoMapper.toDto(contact));
    }

    public static final GuestResponseDto toResponseDto(final Optional<Guest> contact) {
        return new GuestResponseDto().content(contact.map(GuestDtoMapper::toDto)
            .orElse(null));
    }

    public static final GuestPageResponseDto toResponseDto(final Page<Guest> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(GuestDtoMapper::toDto)
            .toList());
        return new GuestPageResponseDto().content(page.content()
            .stream()
            .map(GuestDtoMapper::toDto)
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

    private static final GuestDto toDto(final Guest guest) {
        ContactNameDto name;

        name = new ContactNameDto().firstName(guest.name()
            .firstName())
            .lastName(guest.name()
                .lastName())
            .fullName(guest.name()
                .fullName());

        return new GuestDto().number(guest.number())
            .name(name)
            .games(new ArrayList<>(guest.games()));
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

    private GuestDtoMapper() {
        super();
    }

}
