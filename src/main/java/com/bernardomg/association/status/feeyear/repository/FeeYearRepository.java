
package com.bernardomg.association.status.feeyear.repository;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.status.feeyear.model.FeeYear;
import com.bernardomg.association.status.feeyear.model.FeeYearRange;

public interface FeeYearRepository {

    public Iterable<? extends FeeYear> findAllForYear(final Integer year, final Sort sort);

    public Iterable<? extends FeeYear> findAllForYearWithActiveMember(final Integer year, final Sort sort);

    public FeeYearRange findRange();

    public FeeYearRange findRangeWithActiveMember();

}
