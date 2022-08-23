
package com.bernardomg.association.feeyear.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    public final Iterable<? extends FeeYear> getAll(final Integer year) {
        final Collection<Fee>      readFees;
        final Map<Long, List<Fee>> memberFees;
        final Collection<FeeYear>  years;
        Map<Integer, Fee>          monthFees;
        Collection<FeeMonth>       months;
        List<Fee>                  fees;
        DtoFeeYear                 feeYear;
        DtoFeeMonth                feeMonth;
        Fee                        fee;

        readFees = getAllFees(year);
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(Fee::getMemberId));

        years = new ArrayList<>();
        for (final Long member : memberFees.keySet()) {
            fees = memberFees.get(member);
            feeYear = new DtoFeeYear();
            // TODO: Handle empty list
            feeYear.setMember(fees.iterator()
                .next()
                .getMember());
            feeYear.setMemberId(member);
            feeYear.setYear(year);

            monthFees = fees.stream()
                .collect(Collectors.toMap(Fee::getMonth, Function.identity()));
            months = new ArrayList<>();
            for (Integer month = 1; month <= 12; month++) {
                feeMonth = new DtoFeeMonth();
                feeMonth.setMonth(month);
                if (monthFees.containsKey(month)) {
                    // Existing month
                    fee = monthFees.get(month);
                    feeMonth.setPaid(fee.getPaid());
                } else {
                    // Default month
                    feeMonth.setPaid(false);
                }
                months.add(feeMonth);
            }
            feeYear.setMonths(months);

            years.add(feeYear);
        }

        return years;
    }

    private final Collection<Fee> getAllFees(final Integer year) {
        final PersistentFee entity;
        final Sort          sort;

        entity = new PersistentFee();
        entity.setYear(year);

        // TODO: Tests sorting
        sort = Sort.by(Direction.ASC, "member", "year", "month");

        // TODO: Tests repository
        return feeRepository.findAllWithEmployee(Example.of(entity), sort)
            .stream()
            .collect(Collectors.toList());
    }

}
