/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.security.user.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.security.user.domain.model.UserPerson;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.usecase.validation.UserPersonNameNotEmptyRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.user.domain.exception.MissingUsernameException;
import com.bernardomg.security.user.domain.model.User;
import com.bernardomg.security.user.domain.repository.UserRepository;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

@Service
@Transactional
public final class DefaultUserPersonService implements UserPersonService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(DefaultUserPersonService.class);

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
                throw new MissingUsernameException(username);
            });

        readPerson = personRepository.findOne(personNumber)
            .orElseThrow(() -> {
                log.error("Missing person {}", personNumber);
                throw new MissingPersonException(personNumber);
            });

        userPerson = new UserPerson(personNumber, username);
        assignPersonValidator.validate(userPerson);

        userPersonRepository.assignPerson(readUser.username(), readPerson.number());

        return readPerson;
    }

    @Override
    public final Page<Person> getAvailablePerson(final Pagination pagination, final Sorting sorting) {
        final Page<Person> people;

        log.trace("Reading all available people for pagination {} and sorting {}", pagination, sorting);

        people = userPersonRepository.findAllNotAssigned(pagination, sorting);

        log.trace("Read all available people for pagination {} and sorting {}: {}", pagination, sorting, people);

        return people;
    }

    @Override
    public final Optional<Person> getPerson(final String username) {
        final Optional<Person> person;

        log.trace("Reading person for {}", username);

        if (!userRepository.exists(username)) {
            log.error("Missing user {}", username);
            throw new MissingUsernameException(username);
        }

        person = userPersonRepository.findByUsername(username);

        log.trace("Read person for {}: {}", username, person);

        return person;
    }

    @Override
    public final Person unassignPerson(final String username) {
        final boolean exists;
        final Person  person;

        log.trace("Unassigning person to {}", username);

        exists = userRepository.exists(username);
        if (!exists) {
            log.error("Missing user {}", username);
            throw new MissingUsernameException(username);
        }

        person = userPersonRepository.unassignPerson(username);

        log.trace("Unassigned person to {}", username);

        return person;
    }

}
