
package com.bernardomg.association.status.feeyear.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.status.feeyear.model.FeeYear;
import com.bernardomg.association.status.feeyear.repository.FeeYearRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultFeeYearService implements FeeYearService {

    private final FeeYearRepository repository;

    @Override
    @PreAuthorize("hasAuthority('READ_FEE_YEAR')")
    public final Iterable<? extends FeeYear> getAll(final Integer year, final Sort sort) {
        return repository.findAllForYear(year, sort);
    }

}
