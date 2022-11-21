
package com.bernardomg.association.status.feeyear.service;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.status.feeyear.model.FeeYear;
import com.bernardomg.association.status.feeyear.model.FeeYearRange;

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
