
package com.bernardomg.association.balance.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.balance.model.Balance;
import com.bernardomg.association.balance.model.DtoBalance;
import com.bernardomg.association.balance.model.PersistentBalance;
import com.bernardomg.association.balance.repository.BalanceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultBalanceService implements BalanceService {

    private final BalanceRepository repository;

    @Override
    public final Iterable<Balance> getAll() {
        return repository.findAll()
            .stream()
            .map(this::toBalance)
            .collect(Collectors.toList());
    }

    @Override
    public final Balance getForMonth(final Integer month, final Integer year) {
        // TODO Auto-generated method stub
        return null;
    }

    private final Balance toBalance(final PersistentBalance entity) {
        final DtoBalance data;

        data = new DtoBalance();
        data.setId(entity.getId());
        data.setAmount(entity.getAmount());
        data.setMonth(entity.getMonth());
        data.setYear(entity.getYear());

        return data;
    }

    private final PersistentBalance toPersistentBalance(final Balance data) {
        final PersistentBalance entity;

        entity = new PersistentBalance();
        entity.setId(data.getId());
        entity.setAmount(data.getAmount());
        entity.setMonth(data.getMonth());
        entity.setYear(data.getYear());

        return entity;
    }

}
