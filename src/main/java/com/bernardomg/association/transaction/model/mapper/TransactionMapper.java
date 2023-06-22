
package com.bernardomg.association.transaction.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.transaction.model.request.TransactionCreation;
import com.bernardomg.association.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionCreation transaction);

    @Mapping(target = "id", ignore = true)
    public PersistentTransaction toEntity(final TransactionUpdate transaction);

}
