
package com.bernardomg.association.member.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoPaidMonth;
import com.bernardomg.association.member.model.PaidMonth;
import com.bernardomg.association.member.model.PersistentMemberPeriod;
import com.bernardomg.association.member.model.PersistentPaidMonth;
import com.bernardomg.association.member.repository.MemberPeriodRepository;
import com.bernardomg.association.member.repository.PaidMonthRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultPaidMonthService implements PaidMonthService {

    private final MemberPeriodRepository periodRepository;

    private final PaidMonthRepository    repository;

    @Override
    public final PaidMonth create(final Long member, final PaidMonth month) {
        final PersistentPaidMonth entity;
        final PersistentPaidMonth created;

        entity = toPersistentPaidMonth(month);
        entity.setMember(member);

        created = repository.save(entity);
        return toPaidMonth(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<? extends PaidMonth> getAllForMember(final Long member) {
        final Iterable<PersistentMemberPeriod>  periods;
        final Collection<Collection<PaidMonth>> months;
        Collection<PaidMonth>                   readMonths;
        PersistentPaidMonth                     entity;

        periods = periodRepository.findAll();

        months = new ArrayList<>();
        for (final PersistentMemberPeriod period : periods) {
            entity = new PersistentPaidMonth();
            entity.setMember(member);

            // TODO: Sort by date
            readMonths = repository
                .findInRange(member, period.getStartMonth(), period.getStartYear(), period.getEndMonth(),
                    period.getEndYear())
                .stream()
                .map(this::toPaidMonth)
                .collect(Collectors.toList());
            months.add(readMonths);
        }

        return months.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    private final PaidMonth toPaidMonth(final PersistentPaidMonth entity) {
        final DtoPaidMonth data;

        data = new DtoPaidMonth();
        data.setId(entity.getId());
        data.setMember(entity.getMember());
        data.setMonth(entity.getMonth());
        data.setYear(entity.getYear());
        data.setPaid(true);

        return data;
    }

    private final PersistentPaidMonth toPersistentPaidMonth(final PaidMonth data) {
        final PersistentPaidMonth entity;

        entity = new PersistentPaidMonth();
        entity.setId(data.getId());
        entity.setMember(data.getMember());
        entity.setMonth(data.getMonth());
        entity.setYear(data.getYear());

        return entity;
    }

}
