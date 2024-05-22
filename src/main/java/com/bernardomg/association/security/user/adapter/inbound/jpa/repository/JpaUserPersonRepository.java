
package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserPersonEntity;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.repository.UserSpringRepository;

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
    public final void delete(final String username) {
        final Optional<UserEntity> user;

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            userPersonSpringRepository.deleteByUserId(user.get()
                .getId());
        }
    }

    @Override
    public final boolean existsByPersonForAnotherUser(final String username, final long number) {
        return userPersonSpringRepository.existsByNotUsernameAndMemberNumber(username, number);
    }

    @Override
    public final Collection<Person> findAllNotAssigned(final Pageable page) {
        return userPersonSpringRepository.findAllNotAssigned(page)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Optional<Person> findByUsername(final String username) {
        final Optional<UserEntity>       user;
        final Optional<UserPersonEntity> userMember;
        final Optional<Person>           result;

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            // TODO: Simplify this, use JPA relationships
            userMember = userPersonSpringRepository.findByUserId(user.get()
                .getId());
            if ((userMember.isPresent()) && (userMember.get()
                .getPerson() != null)) {
                result = Optional.of(toDomain(userMember.get()
                    .getPerson()));
            } else {
                result = Optional.empty();
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Person save(final String username, final long number) {
        final UserPersonEntity       userMember;
        final Optional<UserEntity>   user;
        final Optional<PersonEntity> person;
        final Person                 result;

        user = userSpringRepository.findByUsername(username);
        person = personSpringRepository.findByNumber(number);
        if ((user.isPresent()) && (person.isPresent())) {
            userMember = UserPersonEntity.builder()
                .withUserId(person.get()
                    .getId())
                .withPerson(person.get())
                .withUser(user.get())
                .build();
            userPersonSpringRepository.save(userMember);
            result = toDomain(person.get());
        } else {
            result = null;
        }

        return result;
    }

    private final Person toDomain(final PersonEntity entity) {
        final PersonName memberName;

        memberName = PersonName.builder()
            .withFirstName(entity.getName())
            .withLastName(entity.getSurname())
            .build();
        return Person.builder()
            .withNumber(entity.getNumber())
            .withIdentifier(entity.getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPhone())
            .build();
    }

}
