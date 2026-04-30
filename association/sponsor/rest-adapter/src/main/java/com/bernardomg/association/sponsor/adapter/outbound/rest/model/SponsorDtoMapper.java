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

package com.bernardomg.association.sponsor.adapter.outbound.rest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.ContactChannelDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.ContactMethodDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.EditionContactChannelDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SponsorCreationDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SponsorDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SponsorNameDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SponsorPageResponseDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SponsorPatchDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SponsorResponseDto;
import com.bernardomg.association.sponsor.adapter.outbound.rest.dto.SponsorUpdateDto;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.model.Sponsor.ContactChannel;
import com.bernardomg.association.sponsor.domain.model.Sponsor.ContactMethod;
import com.bernardomg.association.sponsor.domain.model.Sponsor.Name;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class SponsorDtoMapper {

    public static final Sponsor toDomain(final long number, final SponsorPatchDto change) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;

        name = new Name(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        contactChannels = change.getContactChannels()
            .stream()
            .map(SponsorDtoMapper::toDomain)
            .toList();

        return new Sponsor(change.getIdentifier(), number, name, null, contactChannels,
            new ArrayList<>(change.getYears()), change.getAddress(), change.getComments(), Set.of());
    }

    public static final Sponsor toDomain(final long number, final SponsorUpdateDto change) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;

        name = new Name(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        contactChannels = change.getContactChannels()
            .stream()
            .map(SponsorDtoMapper::toDomain)
            .toList();

        return new Sponsor(change.getIdentifier(), number, name, null, contactChannels,
            new ArrayList<>(change.getYears()), change.getAddress(), change.getComments(), Set.of());
    }

    public static final Sponsor toDomain(final SponsorCreationDto creation) {
        final Name name;

        name = new Name(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new Sponsor(creation.getIdentifier(), -1L, name, null, List.of(), List.of(), "", "", Set.of());
    }

    public static final SponsorResponseDto toResponseDto(final Optional<Sponsor> contact) {
        return new SponsorResponseDto().content(contact.map(SponsorDtoMapper::toDto)
            .orElse(null));
    }

    public static final SponsorPageResponseDto toResponseDto(final Page<Sponsor> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(SponsorDtoMapper::toDto)
            .toList());
        return new SponsorPageResponseDto().content(page.content()
            .stream()
            .map(SponsorDtoMapper::toDto)
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

    public static final SponsorResponseDto toResponseDto(final Sponsor contact) {
        return new SponsorResponseDto().content(SponsorDtoMapper.toDto(contact));
    }

    private static final ContactChannel toDomain(final EditionContactChannelDto dto) {
        final ContactMethod contactMethod;

        contactMethod = new ContactMethod(dto.getMethod(), "");
        return new ContactChannel(contactMethod, dto.getDetail());
    }

    private static final ContactChannelDto toDto(final ContactChannel contact) {
        ContactMethodDto method;

        method = new ContactMethodDto().number(contact.contactMethod()
            .number())
            .name(contact.contactMethod()
                .name());

        return new ContactChannelDto().detail(contact.detail())
            .method(method);
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

    private static final SponsorDto toDto(final Sponsor sponsor) {
        SponsorNameDto          name;
        List<ContactChannelDto> contactChannels;

        name = new SponsorNameDto().firstName(sponsor.name()
            .firstName())
            .lastName(sponsor.name()
                .lastName())
            .fullName(sponsor.name()
                .fullName());
        contactChannels = sponsor.contactChannels()
            .stream()
            .map(SponsorDtoMapper::toDto)
            .toList();

        return new SponsorDto().identifier(sponsor.identifier())
            .number(sponsor.number())
            .name(name)
            .birthDate(sponsor.birthDate())
            .contactChannels(contactChannels)
            .address(sponsor.address())
            .comments(sponsor.comments())
            .years(new ArrayList<>(sponsor.years()))
            .types(new ArrayList<>(sponsor.types()));
    }

    private SponsorDtoMapper() {
        super();
    }

}
