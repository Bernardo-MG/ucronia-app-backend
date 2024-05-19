
package com.bernardomg.association.security.user.usecase.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.security.user.domain.model.UserPerson;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.usecase.validation.AssignPersonValidator;
import com.bernardomg.security.authentication.user.domain.exception.MissingUserException;
import com.bernardomg.security.authentication.user.domain.model.User;
import com.bernardomg.security.authentication.user.domain.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
public final class DefaultUserPersonService implements UserPersonService {

    private final AssignPersonValidator assignPersonValidator;

    private final PersonRepository      personRepository;

    private final UserPersonRepository  userPersonRepository;

    private final UserRepository        userRepository;

    public DefaultUserPersonService(final UserRepository userRepo, final PersonRepository personRepo,
            final UserPersonRepository userPersonRepo) {
        super();

        userRepository = userRepo;
        personRepository = personRepo;
        userPersonRepository = userPersonRepo;

        assignPersonValidator = new AssignPersonValidator(userPersonRepository);
    }

    @Override
    public final Person assignPerson(final String username, final long personNumber) {
        final Optional<User>   readUser;
        final Optional<Person> readPerson;
        final UserPerson       userPerson;

        log.debug("Assigning person {} to {}", personNumber, username);

        readUser = userRepository.findOne(username);
        if (readUser.isEmpty()) {
            throw new MissingUserException(username);
        }

        readPerson = personRepository.findOne(personNumber);
        if (readPerson.isEmpty()) {
            throw new MissingPersonException(personNumber);
        }

        userPerson = UserPerson.builder()
            .withUsername(username)
            .withNumber(personNumber)
            .build();
        assignPersonValidator.validate(userPerson);

        userPersonRepository.save(readUser.get()
            .getUsername(),
            readPerson.get()
                .getNumber());

        return readPerson.get();
    }

    @Override
    public final Collection<Person> getAvailablePerson(final Pageable page) {
        log.debug("Reading all available persons");
        return userPersonRepository.findAllNotAssigned(page);
    }

    @Override
    public final Optional<Person> getPerson(final String username) {
        final Optional<User> readUser;

        log.debug("Reading person for {}", username);

        readUser = userRepository.findOne(username);
        if (readUser.isEmpty()) {
            throw new MissingUserException(username);
        }

        return userPersonRepository.findByUsername(username);
    }

    @Override
    public final void unassignPerson(final String username) {
        final boolean exists;

        log.debug("Unassigning person to {}", username);

        exists = userRepository.exists(username);
        if (!exists) {
            throw new MissingUserException(username);
        }

        userPersonRepository.delete(username);
    }

}
