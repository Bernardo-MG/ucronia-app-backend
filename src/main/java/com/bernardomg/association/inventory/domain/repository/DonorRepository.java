
package com.bernardomg.association.inventory.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.inventory.domain.model.Donor;

public interface DonorRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsName(final String name);

    public boolean existsNameForAnother(final String name, final long number);

    public Iterable<Donor> findAll(Pageable pageable);

    public long findNextNumber();

    public Optional<Donor> findOne(long number);

    public Donor save(final Donor donor);

}
