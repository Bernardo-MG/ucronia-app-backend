
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.YearMonth;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.ContactNameDto;
import com.bernardomg.ucronia.openapi.model.FeeChangeDto;
import com.bernardomg.ucronia.openapi.model.FeeDto;
import com.bernardomg.ucronia.openapi.model.FeeMemberDto;
import com.bernardomg.ucronia.openapi.model.FeePageDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class FeeDtoMapper {

    public static final Fee toDomain(final FeeChangeDto change, final YearMonth month, final long personNumber) {
        final Fee.Member                person;
        final Optional<Fee.Transaction> transaction;

        person = new Fee.Member(personNumber, null);
        if ((change.getPayment()
            .getIndex() == null)
                && ((change.getPayment()
                    .getDate() == null))) {
            transaction = Optional.empty();
        } else {
            transaction = Optional.of(new Fee.Transaction(change.getPayment()
                .getDate(),
                change.getPayment()
                    .getIndex()));
        }

        return new Fee(month, false, person, transaction);
    }

    public static final FeeDto toDto(final Fee fee) {
        final ContactNameDto contactName;
        final FeeMemberDto   feeMember;

        contactName = new ContactNameDto().firstName(fee.member()
            .name()
            .firstName())
            .lastName(fee.member()
                .name()
                .lastName())
            .fullName(fee.member()
                .name()
                .fullName());
        feeMember = new FeeMemberDto().name(contactName)
            .number(fee.member()
                .number());
        return new FeeDto().month(fee.month())
            .paid(fee.paid())
            .member(feeMember);
    }

    public static final FeePageDto toDto(final Page<Fee> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(FeeDtoMapper::toDto)
            .toList());
        return new FeePageDto().content(page.content()
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
