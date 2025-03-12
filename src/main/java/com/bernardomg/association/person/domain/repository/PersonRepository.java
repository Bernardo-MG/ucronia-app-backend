
package com.bernardomg.association.person.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.query.PersonQuery;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface PersonRepository {

    public void activate(final long number);

    public void activateAll(final Collection<Long> numbers);

    public void deactivate(final long number);

    public void deactivateAll(final Collection<Long> numbers);

    public void delete(final long number);

    public boolean exists(final long number);

    public Iterable<Person> findAll(final Pagination pagination, final Sorting sorting);

    public Iterable<Person> findAll(final PersonQuery query, final Pagination pagination, final Sorting sorting);

    public Collection<Person> findAllToRenew();

    public Collection<Person> findAllWithRenewalMismatch();

    public long findNextNumber();

    public Optional<Person> findOne(final Long number);

    public boolean isActive(final long number);

    public Person save(final Person person);

}
