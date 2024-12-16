
package com.bernardomg.association.fee.usecase.service;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Fee service to allow the users check their own fees.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface MyFeesService {

    /**
     * Returns all the fees for the user in session.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the fees for the user in session
     */
    public Iterable<Fee> getAllForUserInSession(final Pagination pagination, final Sorting sorting);

}
