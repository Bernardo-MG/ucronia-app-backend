
package com.bernardomg.association.security.user.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.security.user.domain.model.UserPerson;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.usecase.validation.UserPersonNameNotEmptyRule;
import com.bernardomg.security.user.data.domain.exception.MissingUserException;
import com.bernardomg.security.user.data.domain.model.User;
import com.bernardomg.security.user.data.domain.repository.UserRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultUserPersonService implements UserPersonService {

    private final Validator<UserPerson> assignPersonValidator;

    private final PersonRepository      personRepository;

    private final UserPersonRepository  userPersonRepository;

    private final UserRepository        userRepository;

    public DefaultUserPersonService(final UserRepository userRepo, final PersonRepository personRepo,
            final UserPersonRepository userPersonRepo) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        personRepository = Objects.requireNonNull(personRepo);
        userPersonRepository = Objects.requireNonNull(userPersonRepo);

        assignPersonValidator = new FieldRuleValidator<>(new UserPersonNameNotEmptyRule(userPersonRepository));
    }

    @Override
    public final Person assignPerson(final String username, final long personNumber) {
        final User       readUser;
        final Person     readPerson;
        final UserPerson userPerson;

        log.debug("Assigning person {} to {}", personNumber, username);

        readUser = userRepository.findOne(username)
            .orElseThrow(() -> {
                log.error("Missing user {}", username);
                throw new MissingUserException(username);
            });

        readPerson = personRepository.findOne(personNumber)
            .orElseThrow(() -> {
                log.error("Missing person {}", personNumber);
                throw new MissingPersonException(personNumber);
            });

        userPerson = new UserPerson(personNumber, username);
        assignPersonValidator.validate(userPerson);

        userPersonRepository.assignPerson(readUser.getUsername(), readPerson.number());

        return readPerson;
    }

    @Override
    public final Iterable<Person> getAvailablePerson(final Pageable page) {
        log.debug("Reading all available persons");
        return userPersonRepository.findAllNotAssigned(page);
    }

    @Override
    public final Optional<Person> getPerson(final String username) {
        log.debug("Reading person for {}", username);

        if (!userRepository.exists(username)) {
            log.error("Missing user {}", username);
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
