
package com.bernardomg.association.fee.usecase.service;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.domain.model.Fee;

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
     * @param pageable
     *            pagination to apply
     * @return all the fees for the user in session
     */
    public Iterable<Fee> getAllForUserInSession(final Pageable pageable);

}
