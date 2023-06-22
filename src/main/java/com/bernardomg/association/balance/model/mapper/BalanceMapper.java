
package com.bernardomg.association.balance.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.association.balance.model.ImmutableMonthlyBalance;
import com.bernardomg.association.balance.model.MonthlyBalance;
import com.bernardomg.association.balance.persistence.model.PersistentMonthlyBalance;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    public default MonthlyBalance toDto(final PersistentMonthlyBalance entity) {
        return ImmutableMonthlyBalance.builder()
            .date(entity.getDate())
            .total(entity.getTotal())
            .cumulative(entity.getCumulative())
            .build();
    }

}
