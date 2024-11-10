
package com.bernardomg.association.person.usecase.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultMemberStatusService implements MemberStatusService {

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
        personRepository.activateAll(toActivate);

        toDeactivate = persons.stream()
            .filter(p -> p.membership()
                .get()
                .active())
            .map(Person::number)
            .toList();
        personRepository.deactivateAll(toDeactivate);
    }

    @Override
    public final void deactivate(final YearMonth date, final Long personNumber) {
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Deactivating member status for person {}", personNumber);
            // If deleting at the current month, the user is set to inactive
            personRepository.deactivate(personNumber);
        }
    }

}
