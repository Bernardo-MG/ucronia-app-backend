
package com.bernardomg.association.inventory.domain.repository;

import com.bernardomg.association.inventory.domain.model.Donor;

public interface DonorRepository {

    public boolean exists(final String name);

    public Donor save(final Donor donor);

}
