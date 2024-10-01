
package com.bernardomg.association.fee.usecase.validation;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the fee's dates are not registed. There is an special case, as dates for unpaid fees are ignored, to allow
 * paying those dates.
 */
@Slf4j
public final class FeeDateNotRegisteredRule implements FieldRule<Collection<Fee>> {

    private final FeeRepository    feeRepository;

    private final PersonRepository personRepository;

    public FeeDateNotRegisteredRule(final PersonRepository personRepo, final FeeRepository feeRepo) {
        super();

        personRepository = Objects.requireNonNull(personRepo);
        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Collection<Fee> fees) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final List<YearMonth>        existing;
        final long                   number;
        final Person                 person;

        if (!fees.isEmpty()) {
            number = fees.iterator()
                .next()
                .person()
                .number();
            person = personRepository.findOne(number)
                .get();
            // TODO: use a single query
            existing = fees.stream()
                .map(Fee::date)
                .filter(date -> feeRepository.existsPaid(person.number(), date))
                .toList();
            if (!existing.isEmpty()) {
                log.error("Dates {} are already registered", existing);
                fieldFailure = FieldFailure.of("feeDates[]", "existing", existing);
                failure = Optional.of(fieldFailure);
            } else {
                failure = Optional.empty();
            }
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
