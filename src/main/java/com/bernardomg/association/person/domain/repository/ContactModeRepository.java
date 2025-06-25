
package com.bernardomg.association.person.domain.repository;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.ContactMode;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface ContactModeRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public Iterable<ContactMode> findAll(final Pagination pagination, final Sorting sorting);

    public long findNextNumber();

    public Optional<ContactMode> findOne(final Long number);

    public ContactMode save(final ContactMode person);

}
