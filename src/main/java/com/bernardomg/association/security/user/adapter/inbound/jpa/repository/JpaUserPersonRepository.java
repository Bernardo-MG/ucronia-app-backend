
package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserPersonEntity;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringSorting;
import com.bernardomg.security.user.data.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.user.data.adapter.inbound.jpa.repository.UserSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaUserPersonRepository implements UserPersonRepository {

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
            userMember = UserPersonEntity.builder()
                .withUserId(user.get()
                    .getId())
                .withPerson(person.get())
                .withUser(user.get())
                .build();
            userPersonSpringRepository.save(userMember);
            result = toDomain(person.get());

            log.trace("Assigned person {} to username {}", number, username);
        } else {
            log.warn("Failed to assign person {} to username {}", number, username);

            result = null;
        }

        return result;
    }

    @Override
    public final void delete(final String username) {
        final Optional<UserEntity> user;

        log.debug("Deleting user {}", username);

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            // TODO: handle relationships
            // TODO: why not delete by username?
            userPersonSpringRepository.deleteByUserId(user.get()
                .getId());

            log.debug("Deleted user {}", username);
        }
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
    public final Iterable<Person> findAllNotAssigned(final Pagination pagination, final Sorting sorting) {
        final Iterable<Person> people;
        final Pageable         pageable;
        final Sort             sort;

        log.trace("Finding all the people with pagination {} and sorting {}", pagination, sorting);

        sort = SpringSorting.toSort(sorting);
        pageable = PageRequest.of(pagination.page(), pagination.size(), sort);
        people = userPersonSpringRepository.findAllNotAssigned(pageable)
            .map(this::toDomain);

        log.trace("Found all the people: {}", people);

        return people;
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
                person = Optional.of(toDomain(userMember.get()
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

    private final Person toDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new Person(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(), entity.getPhone(),
            Optional.empty());
    }

}
