
package com.bernardomg.association.person.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.person.domain.filter.PersonFilter;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface PersonRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByIdentifier(final String identifier);

    public boolean existsByIdentifierForAnother(final long number, final String identifier);

    public Page<Person> findAll(final PersonFilter filter, final Pagination pagination, final Sorting sorting);

    public Collection<Person> findAllToRenew();

    public Collection<Person> findAllWithRenewalMismatch();

    public long findNextNumber();

    public Optional<Person> findOne(final Long number);

    public boolean isActive(final long number);

    public Person save(final Person person);

    public Collection<Person> saveAll(final Collection<Person> persons);

}
