
package com.bernardomg.association.inventory.usecase.service;

import com.bernardomg.association.inventory.domain.model.Donor;

public interface DonorService {

    public Donor create(final Donor donor);

    public void delete(final long number);

    public Donor update(final Donor donor);

}
