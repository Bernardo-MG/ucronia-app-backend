
package com.bernardomg.association.person.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.person.domain.model.Person;

public interface PersonRepository {

    public void activate(final long number);

    public void deactivate(final long number);

    public void delete(final long number);

    public boolean exists(final long number);

    public Iterable<Person> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<Person> findOne(final Long number);

    public boolean isActive(final long number);

    public Person save(final Person person);

}
