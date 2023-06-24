
package com.bernardomg.association.balance.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.association.balance.model.DtoMonthlyBalance;
import com.bernardomg.association.balance.persistence.model.PersistentMonthlyBalance;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    public DtoMonthlyBalance toDto(final PersistentMonthlyBalance entity);

}
