
package com.bernardomg.association.transaction.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.transaction.model.DtoTransaction;
import com.bernardomg.association.transaction.model.request.TransactionCreate;
import com.bernardomg.association.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    public DtoTransaction toDto(final PersistentTransaction transaction);

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionCreate transaction);

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionUpdate transaction);

}
