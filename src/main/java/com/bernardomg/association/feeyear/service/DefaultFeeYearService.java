
package com.bernardomg.association.feeyear.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.model.Fee;
import com.bernardomg.association.fee.model.PersistentFee;
import com.bernardomg.association.fee.repository.FeeRepository;
import com.bernardomg.association.feeyear.model.DtoFeeMonth;
import com.bernardomg.association.feeyear.model.DtoFeeYear;
import com.bernardomg.association.feeyear.model.FeeMonth;
import com.bernardomg.association.feeyear.model.FeeYear;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultFeeYearService implements FeeYearService {

    private final FeeRepository feeRepository;

    @Override
    public final Iterable<? extends FeeYear> getAll(final FeeYear sample) {
        final Collection<Fee>      readFees;
        final Map<Long, List<Fee>> memberFees;
        final Collection<FeeYear>  years;
        Map<Integer, List<Fee>>    yearFees;
        Collection<FeeMonth>       months;
        List<Fee>                  fees;
        DtoFeeYear                 feeYear;

        readFees = getAllFees(sample);
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(Fee::getMemberId));

        years = new ArrayList<>();
        for (final Long member : memberFees.keySet()) {
            fees = memberFees.get(member);
            yearFees = fees.stream()
                .collect(Collectors.groupingBy(Fee::getYear));

            for (final Integer year : yearFees.keySet()) {
                feeYear = new DtoFeeYear();
                // TODO: Handle empty list
                feeYear.setMember(fees.iterator().next().getMember());
                feeYear.setMemberId(member);
                feeYear.setYear(year);

                fees = yearFees.get(year);
                months = fees.stream()
                    .map(this::toFeeMonth)
                    .collect(Collectors.toList());

                feeYear.setMonths(months);

                years.add(feeYear);
            }
        }

        return years;
    }

    private final Collection<Fee> getAllFees(final FeeYear sample) {
        final PersistentFee entity;
        final Sort          sort;

        entity = toFeeEntity(sample);

        sort = Sort.by(Direction.ASC, "member", "month", "year");

        return feeRepository.findAllWithEmployee(Example.of(entity), sort)
            .stream()
            .collect(Collectors.toList());
    }

    private final PersistentFee toFeeEntity(final FeeYear data) {
        final PersistentFee entity;

        entity = new PersistentFee();
        entity.setMember(data.getMemberId());
        entity.setYear(data.getYear());

        return entity;
    }

    private final FeeMonth toFeeMonth(final Fee fee) {
        final DtoFeeMonth month;

        month = new DtoFeeMonth();
        month.setMonth(fee.getMonth());
        month.setPaid(fee.getPaid());

        return month;
    }

}
