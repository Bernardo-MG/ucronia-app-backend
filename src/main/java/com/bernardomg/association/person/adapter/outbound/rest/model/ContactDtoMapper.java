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

package com.bernardomg.association.person.adapter.outbound.rest.model;

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.model.Contact.Membership;
import com.bernardomg.association.person.domain.model.Contact.PersonContact;
import com.bernardomg.association.person.domain.model.ContactName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.MembershipDto;
import com.bernardomg.ucronia.openapi.model.PersonChangeDto;
import com.bernardomg.ucronia.openapi.model.PersonContactDto;
import com.bernardomg.ucronia.openapi.model.PersonCreationDto;
import com.bernardomg.ucronia.openapi.model.PersonDto;
import com.bernardomg.ucronia.openapi.model.PersonPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PersonResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class ContactDtoMapper {

    public static final Contact toDomain(final long number, final PersonChangeDto change) {
        final ContactName          name;
        final Optional<Membership> membership;

        name = new ContactName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        if (change.getMembership() == null) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(change.getMembership()
                .getActive(),
                change.getMembership()
                    .getRenew()));
        }

        return new Contact(change.getIdentifier(), number, name, change.getBirthDate(), membership, List.of());
    }

    public static final Contact toDomain(final PersonCreationDto creation) {
        final ContactName          name;
        final Optional<Membership> membership;

        name = new ContactName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());
        if (creation.getMember() == null) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(true, true));
        }

        return new Contact("", -1L, name, null, membership, List.of());
    }

    public static final PersonResponseDto toResponseDto(final Contact person) {
        return new PersonResponseDto().content(ContactDtoMapper.toDto(person));
    }

    public static final PersonResponseDto toResponseDto(final Optional<Contact> person) {
        return new PersonResponseDto().content(person.map(ContactDtoMapper::toDto)
            .orElse(null));
    }

    public static final PersonPageResponseDto toResponseDto(final Page<Contact> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(ContactDtoMapper::toDto)
            .toList());
        return new PersonPageResponseDto().content(page.content()
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

    private static final PersonDto toDto(final Contact person) {
        ContactNameDto         name;
        MembershipDto          membership;
        List<PersonContactDto> contacts;

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
        contacts = person.contacts()
            .stream()
            .map(ContactDtoMapper::toDto)
            .toList();

        return new PersonDto().identifier(person.identifier())
            .number(person.number())
            .name(name)
            .birthDate(person.birthDate())
            .membership(membership)
            .contacts(contacts);
    }

    private static final PersonContactDto toDto(final PersonContact contact) {
        ContactMethodDto method;

        method = new ContactMethodDto().number(contact.method()
            .number())
            .name(contact.method()
                .name());

        return new PersonContactDto().contact(contact.contact())
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
