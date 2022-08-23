
package com.bernardomg.association.feeyear.service;

import com.bernardomg.association.feeyear.model.FeeYear;

/**
 * Fee service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeYearService {

    /**
     * Returns all the fee years matching the sample. If the sample fields are empty, then all the fee years are
     * returned.
     *
     * @param sample
     *            sample for filtering
     * @return all the fee years matching the sample
     */
    public Iterable<? extends FeeYear> getAll(final FeeYear sample);

}
