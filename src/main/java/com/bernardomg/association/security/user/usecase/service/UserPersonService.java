
package com.bernardomg.association.security.user.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.person.domain.model.Person;

public interface UserPersonService {

    public Person assignPerson(final String username, final long memberId);

    public Iterable<Person> getAvailablePerson(final Pageable page);

    public Optional<Person> getPerson(final String username);

    public void unassignPerson(final String username);

}
