
package com.bernardomg.association.status.feeyear.repository;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.status.feeyear.model.FeeYear;

public interface FeeYearRepository {

    public Iterable<? extends FeeYear> findAllForYear(final Integer year, final Sort sort);

    public Iterable<Integer> findRange();

}
