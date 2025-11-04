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

package com.bernardomg.association.security.user.adapter.outbound.rest.model;

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactChannelDto;
import com.bernardomg.ucronia.openapi.model.ContactDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.ContactPageResponseDto;
import com.bernardomg.ucronia.openapi.model.ContactResponseDto;
import com.bernardomg.ucronia.openapi.model.MembershipDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class UserContactDtoMapper {

    public static final ContactResponseDto toResponseDto(final Contact contact) {
        return new ContactResponseDto().content(UserContactDtoMapper.toDto(contact));
    }

    public static final ContactResponseDto toResponseDto(final Optional<Contact> person) {
        return new ContactResponseDto().content(person.map(UserContactDtoMapper::toDto)
            .orElse(null));
    }

    public static final ContactPageResponseDto toResponseDto(final Page<Contact> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(UserContactDtoMapper::toDto)
            .toList());
        return new ContactPageResponseDto().content(page.content()
            .stream()
            .map(UserContactDtoMapper::toDto)
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

    private static final ContactDto toDto(final Contact person) {
        ContactNameDto          name;
        MembershipDto           membership;
        List<ContactChannelDto> contacts;

        name = new ContactNameDto().firstName(person.name()
            .firstName())
            .lastName(person.name()
                .lastName())
            .fullName(person.name()
                .fullName());
        if (person.membership()
            .isPresent()) {
            membership = new MembershipDto().active(person.membership()
                .get()
                .active())
                .renew(person.membership()
                    .get()
                    .renew());
        } else {
            membership = null;
        }
        contacts = person.contactChannels()
            .stream()
            .map(UserContactDtoMapper::toDto)
            .toList();
        return new ContactDto().identifier(person.identifier())
            .number(person.number())
            .name(name)
            .birthDate(person.birthDate())
            .membership(membership)
            .contactChannels(contacts);
    }

    private static final ContactChannelDto toDto(final ContactChannel contact) {
        ContactMethodDto method;

        method = new ContactMethodDto().number(contact.method()
            .number())
            .name(contact.method()
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

    private UserContactDtoMapper() {
        super();
    }

}
