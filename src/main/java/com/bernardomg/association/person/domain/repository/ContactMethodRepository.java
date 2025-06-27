
package com.bernardomg.association.person.domain.repository;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface ContactMethodRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final long number, final String name);

    public Iterable<ContactMethod> findAll(final Pagination pagination, final Sorting sorting);

    public long findNextNumber();

    public Optional<ContactMethod> findOne(final Long number);

    public ContactMethod save(final ContactMethod person);

}
