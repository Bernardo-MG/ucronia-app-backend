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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactChannelDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.EditionContactChannelDto;
import com.bernardomg.ucronia.openapi.model.MemberFeeTypeDto;
import com.bernardomg.ucronia.openapi.model.MemberProfileCreationDto;
import com.bernardomg.ucronia.openapi.model.MemberProfileDto;
import com.bernardomg.ucronia.openapi.model.MemberProfilePageResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberProfileResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberProfileUpdateDto;
import com.bernardomg.ucronia.openapi.model.ProfileNameDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class MemberProfileDtoMapper {

    public static final MemberProfile toDomain(final long number, final MemberProfileUpdateDto change) {
        final ProfileName                name;
        final Collection<ContactChannel> contactChannels;
        final MemberProfile.FeeType      feeType;

        feeType = new MemberProfile.FeeType(change.getFeeType(), "", 0f);

        name = new ProfileName(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        contactChannels = change.getContactChannels()
            .stream()
            .map(MemberProfileDtoMapper::toDomain)
            .toList();

        return new MemberProfile(change.getIdentifier(), number, name, null, contactChannels, change.getAddress(),
            change.getComments(), change.getActive(), change.getRenew(), feeType, Set.of());
    }

    public static final MemberProfile toDomain(final MemberProfileCreationDto creation) {
        final ProfileName           name;
        final MemberProfile.FeeType feeType;

        feeType = new MemberProfile.FeeType(creation.getFeeType(), "", 0f);

        name = new ProfileName(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new MemberProfile(creation.getIdentifier(), -1L, name, null, List.of(), "", "", true, true, feeType,
            Set.of());
    }

    public static final MemberProfileResponseDto toResponseDto(final MemberProfile contact) {
        return new MemberProfileResponseDto().content(MemberProfileDtoMapper.toDto(contact));
    }

    public static final MemberProfileResponseDto toResponseDto(final Optional<MemberProfile> contact) {
        return new MemberProfileResponseDto().content(contact.map(MemberProfileDtoMapper::toDto)
            .orElse(null));
    }

    public static final MemberProfilePageResponseDto toResponseDto(final Page<MemberProfile> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(MemberProfileDtoMapper::toDto)
            .toList());
        return new MemberProfilePageResponseDto().content(page.content()
            .stream()
            .map(MemberProfileDtoMapper::toDto)
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
        final ContactMethodDto method;

        method = new ContactMethodDto().number(contact.contactMethod()
            .number())
            .name(contact.contactMethod()
                .name());

        return new ContactChannelDto().detail(contact.detail())
            .method(method);
    }

    private static final MemberProfileDto toDto(final MemberProfile memberProfile) {
        final ProfileNameDto          name;
        final List<ContactChannelDto> contactChannels;
        final MemberFeeTypeDto        feeType;

        name = new ProfileNameDto().firstName(memberProfile.name()
            .firstName())
            .lastName(memberProfile.name()
                .lastName())
            .fullName(memberProfile.name()
                .fullName());
        contactChannels = memberProfile.contactChannels()
            .stream()
            .map(MemberProfileDtoMapper::toDto)
            .toList();

        feeType = new MemberFeeTypeDto();
        feeType.number(memberProfile.feeType()
            .number());
        feeType.name(memberProfile.feeType()
            .name());
        feeType.amount(memberProfile.feeType()
            .amount());

        return new MemberProfileDto().identifier(memberProfile.identifier())
            .number(memberProfile.number())
            .name(name)
            .birthDate(memberProfile.birthDate())
            .contactChannels(contactChannels)
            .address(memberProfile.address())
            .comments(memberProfile.comments())
            .active(memberProfile.active())
            .renew(memberProfile.renew())
            .feeType(feeType)
            .types(new ArrayList<>(memberProfile.types()));
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

    private MemberProfileDtoMapper() {
        super();
    }

}
