
package com.bernardomg.association.status.feeyear.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.status.feeyear.model.FeeYear;
import com.bernardomg.association.status.feeyear.model.FeeYearRange;
import com.bernardomg.association.status.feeyear.repository.FeeYearRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultFeeYearService implements FeeYearService {

    private final FeeYearRepository repository;

    @Override
    @PreAuthorize("hasAuthority('READ_FEE_YEAR')")
    public final Iterable<? extends FeeYear> getAll(final Integer year, final Boolean onlyActive, final Sort sort) {
        final Iterable<? extends FeeYear> result;

        if (onlyActive) {
            result = repository.findAllForYearWithActiveMember(year, sort);
        } else {
            result = repository.findAllForYear(year, sort);
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('READ_FEE_YEAR')")
    public final FeeYearRange getRange(final Boolean onlyActive) {
        final FeeYearRange range;

        if (onlyActive) {
            range = repository.findRangeWithActiveMember();
        } else {
            range = repository.findRange();
        }

        return range;
    }

}
