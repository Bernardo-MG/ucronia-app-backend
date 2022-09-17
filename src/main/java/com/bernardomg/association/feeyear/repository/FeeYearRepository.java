
package com.bernardomg.association.feeyear.repository;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.feeyear.model.FeeYear;

public interface FeeYearRepository {

    public Iterable<? extends FeeYear> findAllForYear(final Integer year, final Sort sort);

}
