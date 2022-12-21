
package com.bernardomg.association.domain.feeyear.service;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.domain.feeyear.model.FeeYear;
import com.bernardomg.association.domain.feeyear.model.FeeYearRange;

/**
 * Fee service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeYearService {

    public Iterable<? extends FeeYear> getAll(final Integer year, final Boolean onlyActive, final Sort sort);

    public FeeYearRange getRange(final Boolean onlyActive);

}
