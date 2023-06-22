
package com.bernardomg.association.transaction.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.TransactionCreation;
import com.bernardomg.association.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    public default Transaction toDto(final PersistentTransaction transaction) {
        return ImmutableTransaction.builder()
            .id(transaction.getId())
            .description(transaction.getDescription())
            .date(transaction.getDate())
            .amount(transaction.getAmount())
            .build();
    }

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionCreation transaction);

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionUpdate transaction);

}
