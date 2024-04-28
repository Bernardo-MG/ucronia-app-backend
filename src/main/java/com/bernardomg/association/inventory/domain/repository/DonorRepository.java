
package com.bernardomg.association.inventory.domain.repository;

import com.bernardomg.association.inventory.domain.model.Donor;

public interface DonorRepository {

    public boolean exists(final long number);

    public boolean existsName(final String name);

    public boolean existsNameForAnother(final String name, final long number);

    public long findNextNumber();

    public Donor save(final Donor donor);

}
