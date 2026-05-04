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

import com.bernardomg.association.member.adapter.outbound.rest.dto.ContactChannelDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.ContactMethodDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.EditionContactChannelDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberCreationDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberFeeTypeDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberNameDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberPageResponseDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberResponseDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberUpdateDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.member.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.ContactChannel;
import com.bernardomg.association.member.domain.model.Member.ContactMethod;
import com.bernardomg.association.member.domain.model.Member.Name;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class MemberDtoMapper {

    public static final Member toDomain(final long number, final MemberUpdateDto change) {
        final Name                       name;
        final Collection<ContactChannel> contactChannels;
        final Member.FeeType             feeType;

        feeType = new Member.FeeType(change.getFeeType(), "", 0f);

        name = new Name(change.getName()
            .getFirstName(),
            change.getName()
                .getLastName());
        contactChannels = change.getContactChannels()
            .stream()
            .map(MemberDtoMapper::toDomain)
            .toList();

        return new Member(change.getIdentifier(), number, name, null, contactChannels, change.getAddress(),
            change.getComments(), change.getActive(), change.getRenew(), feeType, Set.of());
    }

    public static final Member toDomain(final MemberCreationDto creation) {
        final Name           name;
        final Member.FeeType feeType;

        feeType = new Member.FeeType(creation.getFeeType(), "", 0f);

        name = new Name(creation.getName()
            .getFirstName(),
            creation.getName()
                .getLastName());

        return new Member(creation.getIdentifier(), -1L, name, null, List.of(), "", "", true, true, feeType, Set.of());
    }

    public static final MemberResponseDto toResponseDto(final Member contact) {
        return new MemberResponseDto().content(MemberDtoMapper.toDto(contact));
    }

    public static final MemberResponseDto toResponseDto(final Optional<Member> contact) {
        return new MemberResponseDto().content(contact.map(MemberDtoMapper::toDto)
            .orElse(null));
    }

    public static final MemberPageResponseDto toResponseDto(final Page<Member> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(MemberDtoMapper::toDto)
            .toList());
        return new MemberPageResponseDto().content(page.content()
            .stream()
            .map(MemberDtoMapper::toDto)
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

    private static final MemberDto toDto(final Member Member) {
        final MemberNameDto           name;
        final List<ContactChannelDto> contactChannels;
        final MemberFeeTypeDto        feeType;

        name = new MemberNameDto().firstName(Member.name()
            .firstName())
            .lastName(Member.name()
                .lastName())
            .fullName(Member.name()
                .fullName());
        contactChannels = Member.contactChannels()
            .stream()
            .map(MemberDtoMapper::toDto)
            .toList();

        feeType = new MemberFeeTypeDto();
        feeType.number(Member.feeType()
            .number());
        feeType.name(Member.feeType()
            .name());
        feeType.amount(Member.feeType()
            .amount());

        return new MemberDto().identifier(Member.identifier())
            .number(Member.number())
            .name(name)
            .birthDate(Member.birthDate())
            .contactChannels(contactChannels)
            .address(Member.address())
            .comments(Member.comments())
            .active(Member.active())
            .renew(Member.renew())
            .feeType(feeType)
            .types(new ArrayList<>(Member.types()));
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

    private MemberDtoMapper() {
        super();
    }

}
