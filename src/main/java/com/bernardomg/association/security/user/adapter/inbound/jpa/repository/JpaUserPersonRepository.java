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

package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntityMapper;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserPersonEntity;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.security.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.user.adapter.inbound.jpa.repository.UserSpringRepository;

@Repository
@Transactional
public final class JpaUserPersonRepository implements UserPersonRepository {

    /**
     * Logger for the class.
     */
    private static final Logger              log = LoggerFactory.getLogger(JpaUserPersonRepository.class);

    private final PersonSpringRepository     personSpringRepository;

    private final UserPersonSpringRepository userPersonSpringRepository;

    private final UserSpringRepository       userSpringRepository;

    public JpaUserPersonRepository(final UserPersonSpringRepository userPersonSpringRepo,
            final UserSpringRepository userSpringRepo, final PersonSpringRepository personSpringRepo) {
        super();

        userPersonSpringRepository = Objects.requireNonNull(userPersonSpringRepo);
        userSpringRepository = Objects.requireNonNull(userSpringRepo);
        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final Person assignPerson(final String username, final long number) {
        final UserPersonEntity       userMember;
        final Optional<UserEntity>   user;
        final Optional<PersonEntity> person;
        final Person                 result;

        log.trace("Assigning person {} to username {}", number, username);

        user = userSpringRepository.findByUsername(username);
        person = personSpringRepository.findByNumber(number);
        if ((user.isPresent()) && (person.isPresent())) {
            userMember = new UserPersonEntity();
            userMember.setUserId(user.get()
                .getId());
            userMember.setPerson(person.get());
            userMember.setUser(user.get());

            userPersonSpringRepository.save(userMember);
            result = PersonEntityMapper.toDomain(person.get());

            log.trace("Assigned person {} to username {}", number, username);
        } else {
            log.warn("Failed to assign person {} to username {}", number, username);

            result = null;
        }

        return result;
    }

    @Override
    public final boolean existsByPersonForAnotherUser(final String username, final long number) {
        final boolean exists;

        log.trace("Checking if username {} exists for a user with a number distinct from {}", username, number);

        exists = userPersonSpringRepository.existsByNotUsernameAndMemberNumber(username, number);

        log.trace("Username {} exists for a user with a number distinct from {}: {}", username, number, exists);

        return exists;
    }

    @Override
    public final Page<Person> findAllNotAssigned(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Person> read;
        final Pageable                                     pageable;

        log.trace("Finding all the people with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = userPersonSpringRepository.findAllNotAssigned(pageable)
            .map(PersonEntityMapper::toDomain);

        log.trace("Found all the people: {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Person> findByUsername(final String username) {
        final Optional<UserEntity>       user;
        final Optional<UserPersonEntity> userMember;
        final Optional<Person>           person;

        log.trace("Finding person for username {}", username);

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            // TODO: Simplify this, use JPA relationships
            userMember = userPersonSpringRepository.findByUserId(user.get()
                .getId());
            if ((userMember.isPresent()) && (userMember.get()
                .getPerson() != null)) {
                person = Optional.of(PersonEntityMapper.toDomain(userMember.get()
                    .getPerson()));
            } else {
                person = Optional.empty();
            }
        } else {
            person = Optional.empty();
        }

        log.trace("Found person for username {}: {}", username, person);

        return person;
    }

    @Override
    public final Person unassignPerson(final String username) {
        final Optional<UserEntity> user;
        final Person               person;

        log.debug("Deleting user {}", username);

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            person = findByUsername(username).orElse(null);

            // TODO: handle relationships
            // TODO: why not delete by username?
            userPersonSpringRepository.deleteByUserId(user.get()
                .getId());

            log.debug("Deleted user {}", username);
        } else {
            person = null;
        }

        return person;
    }

}
