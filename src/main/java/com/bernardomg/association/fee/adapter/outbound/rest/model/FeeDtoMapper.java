
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeChangeDto;
import com.bernardomg.ucronia.openapi.model.FeeDto;
import com.bernardomg.ucronia.openapi.model.FeePageResponseDto;
import com.bernardomg.ucronia.openapi.model.FeeResponseDto;
import com.bernardomg.ucronia.openapi.model.FeesResponseDto;
import com.bernardomg.ucronia.openapi.model.MinimalContactDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class FeeDtoMapper {

    public static final Fee toDomain(final FeeChangeDto change, final YearMonth month, final long personNumber) {
        final Fee.Member                person;
        final Optional<Fee.Transaction> transaction;

        person = new Fee.Member(personNumber, null);
        if ((change.getTransaction()
            .getIndex() == null)
                && ((change.getTransaction()
                    .getDate() == null))) {
            transaction = Optional.empty();
        } else {
            transaction = Optional.of(new Fee.Transaction(change.getTransaction()
                .getDate(),
                change.getTransaction()
                    .getIndex()));
        }

        return new Fee(month, false, person, transaction);
    }

    public static final FeesResponseDto toResponseDto(final Collection<Fee> fees) {
        return new FeesResponseDto().content(fees.stream()
            .map(FeeDtoMapper::toDto)
            .toList());
    }

    public static final FeeResponseDto toResponseDto(final Fee fee) {
        return new FeeResponseDto().content(toDto(fee));
    }

    public static final FeeResponseDto toResponseDto(final Optional<Fee> fee) {
        return new FeeResponseDto().content(fee.map(FeeDtoMapper::toDto)
            .orElse(null));
    }

    public static final FeePageResponseDto toResponseDto(final Page<Fee> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(FeeDtoMapper::toDto)
            .toList());
        return new FeePageResponseDto().content(page.content()
            .stream()
            .map(FeeDtoMapper::toDto)
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

    private static final FeeDto toDto(final Fee fee) {
        final ContactNameDto    contactName;
        final MinimalContactDto member;

        contactName = new ContactNameDto().firstName(fee.member()
            .name()
            .firstName())
            .lastName(fee.member()
                .name()
                .lastName())
            .fullName(fee.member()
                .name()
                .fullName());
        member = new MinimalContactDto().name(contactName)
            .number(fee.member()
                .number());
        return new FeeDto().month(fee.month())
            .paid(fee.paid())
            .member(member);
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

    private FeeDtoMapper() {
        super();
    }

}
