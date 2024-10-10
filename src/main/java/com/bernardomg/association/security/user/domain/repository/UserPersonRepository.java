
package com.bernardomg.association.security.user.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.person.domain.model.Person;

public interface UserPersonRepository {

    public Person assignPerson(final String username, final long number);

    public void delete(final String username);

    public boolean existsByPersonForAnotherUser(final String username, final long number);

    public Iterable<Person> findAllNotAssigned(final Pageable page);

    public Optional<Person> findByUsername(final String username);

}
