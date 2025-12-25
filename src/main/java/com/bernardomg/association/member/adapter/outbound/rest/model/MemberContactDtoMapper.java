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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactChannelDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.EditionContactChannelDto;
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
        final ContactName                name;
        final Collection<ContactChannel> contactChannels;

        name = new ContactName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        contactChannels = change.getContactChannels()
            .stream()
            .map(MemberContactDtoMapper::toDomain)
            .toList();

        return new MemberContact(change.getIdentifier(), number, name, null, contactChannels, change.getComments(),
            change.getActive(), change.getRenew());
    }

    public static final MemberContact toDomain(final MemberContactCreationDto creation) {
        final ContactName name;

        name = new ContactName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new MemberContact(creation.getIdentifier(), -1L, name, null, List.of(), "", true, true);
    }

    public static final MemberContactResponseDto toResponseDto(final MemberContact contact) {
        return new MemberContactResponseDto().content(MemberContactDtoMapper.toDto(contact));
    }

    public static final MemberContactResponseDto toResponseDto(final Optional<MemberContact> contact) {
        return new MemberContactResponseDto().content(contact.map(MemberContactDtoMapper::toDto)
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

    private static final MemberContactDto toDto(final MemberContact memberContact) {
        ContactNameDto          name;
        List<ContactChannelDto> contactChannels;

        name = new ContactNameDto().firstName(memberContact.name()
            .firstName())
            .lastName(memberContact.name()
                .lastName())
            .fullName(memberContact.name()
                .fullName());
        contactChannels = memberContact.contactChannels()
            .stream()
            .map(MemberContactDtoMapper::toDto)
            .toList();

        return new MemberContactDto().identifier(memberContact.identifier())
            .number(memberContact.number())
            .name(name)
            .birthDate(memberContact.birthDate())
            .contactChannels(contactChannels)
            .comments(memberContact.comments())
            .active(memberContact.active())
            .renew(memberContact.renew());
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
