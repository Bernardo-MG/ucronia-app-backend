
package com.bernardomg.association.security.user.domain.repository;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface UserPersonRepository {

    public Person assignPerson(final String username, final long number);

    public void delete(final String username);

    public boolean existsByPersonForAnotherUser(final String username, final long number);

    public Page<Person> findAllNotAssigned(final Pagination pagination, final Sorting sorting);

    public Optional<Person> findByUsername(final String username);

}
