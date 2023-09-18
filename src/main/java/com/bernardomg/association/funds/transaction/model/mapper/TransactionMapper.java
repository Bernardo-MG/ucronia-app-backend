
package com.bernardomg.association.funds.transaction.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.funds.transaction.model.ImmutableTransaction;
import com.bernardomg.association.funds.transaction.model.request.TransactionCreate;
import com.bernardomg.association.funds.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    public ImmutableTransaction toDto(final PersistentTransaction transaction);

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionCreate transaction);

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionUpdate transaction);

}
