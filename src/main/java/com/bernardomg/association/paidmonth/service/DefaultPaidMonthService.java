
package com.bernardomg.association.paidmonth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.memberperiod.model.PersistentMemberPeriod;
import com.bernardomg.association.memberperiod.repository.MemberPeriodRepository;
import com.bernardomg.association.paidmonth.model.DtoPaidMonth;
import com.bernardomg.association.paidmonth.model.PaidMonth;
import com.bernardomg.association.paidmonth.model.PersistentPaidMonth;
import com.bernardomg.association.paidmonth.repository.PaidMonthRepository;

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

        // TODO: Check the member exists
        // TODO: Reject invalid months
        // TODO: Reject months out of period

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
        Map<String, PaidMonth>                  monthsByDate;
        Collection<PaidMonth>                   periodMonths;
        PersistentPaidMonth                     entity;
        Function<PaidMonth, String>             keyMapper;
        String                                  key;
        DtoPaidMonth                            unpaidMonth;

        periods = periodRepository.findAll();

        keyMapper = m -> String.format("%d %d", m.getMonth(), m.getYear());
        months = new ArrayList<>();
        for (final PersistentMemberPeriod period : periods) {
            entity = new PersistentPaidMonth();
            entity.setMember(member);

            // TODO: Sort by date
            monthsByDate = repository
                .findInRange(member, period.getStartMonth(), period.getStartYear(), period.getEndMonth(),
                    period.getEndYear())
                .stream()
                .map(this::toPaidMonth)
                .collect(Collectors.toMap(keyMapper, Function.identity()));
            periodMonths = new ArrayList<>();
            for (Integer year = period.getStartYear(); year <= period.getEndYear(); year++) {
                for (Integer month = period.getStartMonth(); month <= period.getEndMonth(); month++) {
                    key = String.format("%d %d", month, year);
                    if (monthsByDate.containsKey(key)) {
                        periodMonths.add(monthsByDate.get(key));
                    } else {
                        unpaidMonth = new DtoPaidMonth();
                        unpaidMonth.setMember(member);
                        unpaidMonth.setMonth(month);
                        unpaidMonth.setYear(year);
                        unpaidMonth.setPaid(false);
                        periodMonths.add(unpaidMonth);
                    }
                }
            }

            months.add(periodMonths);
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
