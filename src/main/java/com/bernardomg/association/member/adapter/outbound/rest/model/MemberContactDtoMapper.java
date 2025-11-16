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

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactChannelDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.MemberContactChangeDto;
import com.bernardomg.ucronia.openapi.model.MemberContactCreationDto;
import com.bernardomg.ucronia.openapi.model.MemberContactDto;
import com.bernardomg.ucronia.openapi.model.MemberContactPageResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberContactResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class MemberContactDtoMapper {

    public static final MemberContact toDomain(final long number, final MemberContactChangeDto change) {
        final ContactName name;

        name = new ContactName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());

        return new MemberContact(change.getIdentifier(), number, name, change.getBirthDate(), change.getActive(),
            change.getRenew(), List.of());
    }

    public static final MemberContact toDomain(final MemberContactCreationDto creation) {
        final ContactName name;

        name = new ContactName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new MemberContact("", -1L, name, null, creation.getActive(), creation.getActive(), List.of());
    }

    public static final MemberContactResponseDto toResponseDto(final MemberContact member) {
        return new MemberContactResponseDto().content(MemberContactDtoMapper.toDto(member));
    }

    public static final MemberContactResponseDto toResponseDto(final Optional<MemberContact> member) {
        return new MemberContactResponseDto().content(member.map(MemberContactDtoMapper::toDto)
            .orElse(null));
    }

    public static final MemberContactPageResponseDto toResponseDto(final Page<MemberContact> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(MemberContactDtoMapper::toDto)
            .toList());
        return new MemberContactPageResponseDto().content(page.content()
            .stream()
            .map(MemberContactDtoMapper::toDto)
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

    private static final ContactChannelDto toDto(final ContactChannel contact) {
        ContactMethodDto method;

        method = new ContactMethodDto().number(contact.contactMethod()
            .number())
            .name(contact.contactMethod()
                .name());

        return new ContactChannelDto().detail(contact.detail())
            .method(method);
    }

    private static final MemberContactDto toDto(final MemberContact member) {
        ContactNameDto          name;
        List<ContactChannelDto> contactChannels;

        name = new ContactNameDto().firstName(member.name()
            .firstName())
            .lastName(member.name()
                .lastName())
            .fullName(member.name()
                .fullName());
        contactChannels = member.contactChannels()
            .stream()
            .map(MemberContactDtoMapper::toDto)
            .toList();

        return new MemberContactDto().identifier(member.identifier())
            .number(member.number())
            .name(name)
            .birthDate(member.birthDate())
            .contactChannels(contactChannels)
            .active(member.active())
            .renew(member.renew());
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

    private MemberContactDtoMapper() {
        super();
    }

}
