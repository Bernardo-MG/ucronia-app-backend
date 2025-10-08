
package com.bernardomg.association.person.usecase.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.Membership;
import com.bernardomg.association.person.domain.repository.PersonRepository;

@Service
@Transactional
public final class DefaultMemberStatusService implements MemberStatusService {

    /**
     * Logger for the class.
     */
    private static final Logger    log = LoggerFactory.getLogger(DefaultMemberStatusService.class);

    private final PersonRepository personRepository;

    public DefaultMemberStatusService(final PersonRepository personRepo) {
        super();

        personRepository = Objects.requireNonNull(personRepo);
    }

    @Override
    public final void activate(final YearMonth date, final Long personNumber) {
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Activating member status for person {}", personNumber);
            // If paying for the current month, the user is set to active
            // TODO: modify in the service and save
            personRepository.activate(personNumber);
        }
    }

    @Override
    public final void applyRenewal() {
        final Collection<Person> persons;
        final Collection<Long>   toActivate;
        final Collection<Long>   toDeactivate;

        log.debug("Applying membership renewals");

        persons = personRepository.findAllWithRenewalMismatch();

        toActivate = persons.stream()
            .filter(p -> !p.membership()
                .get()
                .active())
            .map(Person::number)
            .toList();
        // TODO: activate outside the repo
        personRepository.activateAll(toActivate);

        toDeactivate = persons.stream()
            .filter(p -> p.membership()
                .get()
                .active())
            .map(Person::number)
            .toList();
        // TODO: deactivate outside the repo
        personRepository.deactivateAll(toDeactivate);
    }

    @Override
    public final void deactivate(final YearMonth date, final Long personNumber) {
        final Optional<Person> person;
        final Person           disabled;
        final Membership       membership;

        // If deleting at the current month, the user is set to inactive
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Deactivating membership for {}", personNumber);
            person = personRepository.findOne(personNumber);

            if (person.isEmpty()) {
                log.warn("Missing person {}", personNumber);
            } else {
                membership = new Membership(false, false);
                disabled = new Person(person.get()
                    .identifier(), personNumber,
                    person.get()
                        .name(),
                    person.get()
                        .birthDate(),
                    Optional.of(membership), person.get()
                        .contacts());
                personRepository.save(disabled);
            }
        }
    }

}
