
package com.bernardomg.association.transaction.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;
import com.bernardomg.ucronia.openapi.model.TransactionChangeDto;
import com.bernardomg.ucronia.openapi.model.TransactionCreationDto;
import com.bernardomg.ucronia.openapi.model.TransactionDto;
import com.bernardomg.ucronia.openapi.model.TransactionPageResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionResponseDto;

public final class TransactionDtoMapper {

    public static final Transaction toDomain(final Long index, final TransactionChangeDto change) {
        return new Transaction(index, change.getDate(), change.getAmount(), change.getDescription());
    }

    public static final Transaction toDomain(final TransactionCreationDto creation) {
        return new Transaction(-1, creation.getDate(), creation.getAmount(), creation.getDescription());
    }

    public static final TransactionResponseDto toResponseDto(final Optional<Transaction> transaction) {
        return new TransactionResponseDto().content(transaction.map(TransactionDtoMapper::toDto)
            .orElse(null));
    }

    public static final TransactionPageResponseDto toResponseDto(final Page<Transaction> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(TransactionDtoMapper::toDto)
            .toList());
        return new TransactionPageResponseDto().content(page.content()
            .stream()
            .map(TransactionDtoMapper::toDto)
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

    public static final TransactionResponseDto toResponseDto(final Transaction transaction) {
        return new TransactionResponseDto().content(TransactionDtoMapper.toDto(transaction));
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

    private static final TransactionDto toDto(final Transaction transaction) {
        return new TransactionDto().index(transaction.index())
            .date(transaction.date())
            .amount(transaction.amount())
            .description(transaction.description());
    }

    private TransactionDtoMapper() {
        super();
    }

}
