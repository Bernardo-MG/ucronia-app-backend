
package com.bernardomg.association.funds.balance.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.association.funds.balance.model.DtoMonthlyBalance;
import com.bernardomg.association.funds.balance.persistence.model.PersistentMonthlyBalance;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    public DtoMonthlyBalance toDto(final PersistentMonthlyBalance entity);

}
