
package com.bernardomg.association.person.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactMethodChangeDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodCreationDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodPageResponseDto;
import com.bernardomg.ucronia.openapi.model.ContactMethodResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class ContactMethodDtoMapper {

    public static final ContactMethod toDomain(final ContactMethodCreationDto creation) {
        return new ContactMethod(null, creation.getName());
    }

    public static final ContactMethod toDomain(final Long number, final ContactMethodChangeDto change) {
        return new ContactMethod(number, change.getName());
    }

    public static final ContactMethodResponseDto toResponseDto(final ContactMethod contactMethod) {
        return new ContactMethodResponseDto().content(ContactMethodDtoMapper.toDto(contactMethod));
    }

    public static final ContactMethodResponseDto toResponseDto(final Optional<ContactMethod> contactMethod) {
        return new ContactMethodResponseDto().content(contactMethod.map(ContactMethodDtoMapper::toDto)
            .orElse(null));
    }

    public static final ContactMethodPageResponseDto toResponseDto(final Page<ContactMethod> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(ContactMethodDtoMapper::toDto)
            .toList());
        return new ContactMethodPageResponseDto().content(page.content()
            .stream()
            .map(ContactMethodDtoMapper::toDto)
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

    private static final ContactMethodDto toDto(final ContactMethod contactMethod) {
        return new ContactMethodDto(contactMethod.number(), contactMethod.name());
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

    private ContactMethodDtoMapper() {
        super();
    }

}
