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

package com.bernardomg.association.profile.adapter.outbound.rest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactChannelDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.EditionContactChannelDto;
import com.bernardomg.ucronia.openapi.model.ProfileChangeDto;
import com.bernardomg.ucronia.openapi.model.ProfileCreationDto;
import com.bernardomg.ucronia.openapi.model.ProfileDto;
import com.bernardomg.ucronia.openapi.model.ProfileNameDto;
import com.bernardomg.ucronia.openapi.model.ProfilePageResponseDto;
import com.bernardomg.ucronia.openapi.model.ProfileResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class ProfileDtoMapper {

    public static final Profile toDomain(final long number, final ProfileChangeDto change) {
        final ProfileName                name;
        final Collection<ContactChannel> contactChannels;

        name = new ProfileName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        contactChannels = change.getContactChannels()
            .stream()
            .map(ProfileDtoMapper::toDomain)
            .toList();

        return new Profile(change.getIdentifier(), number, name, change.getBirthDate(), contactChannels,
            change.getComments(), Set.of());
    }

    public static final Profile toDomain(final ProfileCreationDto creation) {
        final ProfileName name;

        name = new ProfileName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new Profile(creation.getIdentifier(), -1L, name, null, List.of(), "", Set.of());
    }

    public static final ProfileResponseDto toResponseDto(final Optional<Profile> contact) {
        return new ProfileResponseDto().content(contact.map(ProfileDtoMapper::toDto)
            .orElse(null));
    }

    public static final ProfilePageResponseDto toResponseDto(final Page<Profile> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(ProfileDtoMapper::toDto)
            .toList());
        return new ProfilePageResponseDto().content(page.content()
            .stream()
            .map(ProfileDtoMapper::toDto)
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

    public static final ProfileResponseDto toResponseDto(final Profile contact) {
        return new ProfileResponseDto().content(ProfileDtoMapper.toDto(contact));
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

    private static final ProfileDto toDto(final Profile contact) {
        ProfileNameDto          name;
        List<ContactChannelDto> contactChannels;

        name = new ProfileNameDto().firstName(contact.name()
            .firstName())
            .lastName(contact.name()
                .lastName())
            .fullName(contact.name()
                .fullName());
        contactChannels = contact.contactChannels()
            .stream()
            .map(ProfileDtoMapper::toDto)
            .toList();

        return new ProfileDto().identifier(contact.identifier())
            .number(contact.number())
            .name(name)
            .birthDate(contact.birthDate())
            .contactChannels(contactChannels)
            .comments(contact.comments())
            .types(new ArrayList<>(contact.types()));
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

    private ProfileDtoMapper() {
        super();
    }

}
