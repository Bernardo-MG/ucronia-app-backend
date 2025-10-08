
package com.bernardomg.association.member.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.MemberDto;
import com.bernardomg.ucronia.openapi.model.MemberPageResponseDto;
import com.bernardomg.ucronia.openapi.model.MemberResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class MemberDtoMapper {

    public static final MemberResponseDto toResponseDto(final Optional<Member> member) {
        return new MemberResponseDto().content(member.map(MemberDtoMapper::toDto)
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

    private static final MemberDto toDto(final Member member) {
        final ContactNameDto contactName;

        contactName = new ContactNameDto().firstName(member.name()
            .firstName())
            .lastName(member.name()
                .lastName())
            .fullName(member.name()
                .fullName());
        return new MemberDto().number(member.number())
            .name(contactName);
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
