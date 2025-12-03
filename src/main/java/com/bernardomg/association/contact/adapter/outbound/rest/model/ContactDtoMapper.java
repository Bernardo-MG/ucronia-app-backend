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

package com.bernardomg.association.contact.adapter.outbound.rest.model;

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.Contact.ContactChannel;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactChangeDto;
import com.bernardomg.ucronia.openapi.model.ContactChannelDto;
import com.bernardomg.ucronia.openapi.model.ContactCreationDto;
import com.bernardomg.ucronia.openapi.model.ContactDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.ContactPageResponseDto;
import com.bernardomg.ucronia.openapi.model.ContactResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class ContactDtoMapper {

    public static final Contact toDomain(final ContactCreationDto creation) {
        final ContactName name;

        name = new ContactName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new Contact(creation.getIdentifier(), -1L, name, null, List.of());
    }

    public static final Contact toDomain(final long number, final ContactChangeDto change) {
        final ContactName name;

        name = new ContactName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());

        return new Contact(change.getIdentifier(), number, name, change.getBirthDate(), List.of());
    }

    public static final ContactResponseDto toResponseDto(final Contact contact) {
        return new ContactResponseDto().content(ContactDtoMapper.toDto(contact));
    }

    public static final ContactResponseDto toResponseDto(final Optional<Contact> contact) {
        return new ContactResponseDto().content(contact.map(ContactDtoMapper::toDto)
            .orElse(null));
    }

    public static final ContactPageResponseDto toResponseDto(final Page<Contact> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(ContactDtoMapper::toDto)
            .toList());
        return new ContactPageResponseDto().content(page.content()
            .stream()
            .map(ContactDtoMapper::toDto)
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

    private static final ContactDto toDto(final Contact contact) {
        ContactNameDto          name;
        List<ContactChannelDto> contactChannels;

        name = new ContactNameDto().firstName(contact.name()
            .firstName())
            .lastName(contact.name()
                .lastName())
            .fullName(contact.name()
                .fullName());
        contactChannels = contact.contactChannels()
            .stream()
            .map(ContactDtoMapper::toDto)
            .toList();

        return new ContactDto().identifier(contact.identifier())
            .number(contact.number())
            .name(name)
            .birthDate(contact.birthDate())
            .contactChannels(contactChannels);
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

    private ContactDtoMapper() {
        super();
    }

}
