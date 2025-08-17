
package com.bernardomg.association.security.user.usecase.service;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface UserPersonService {

    public Person assignPerson(final String username, final long memberId);

    public Page<Person> getAvailablePerson(final Pagination pagination, final Sorting sorting);

    public Optional<Person> getPerson(final String username);

    public void unassignPerson(final String username);

}
