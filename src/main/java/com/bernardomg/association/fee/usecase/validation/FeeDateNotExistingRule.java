
package com.bernardomg.association.fee.usecase.validation;

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
 * Checks the fee's date is not registered.
 */
@Slf4j
public final class FeeDateNotExistingRule implements FieldRule<Fee> {

    private final FeeRepository    feeRepository;

    private final PersonRepository personRepository;

    public FeeDateNotExistingRule(final PersonRepository personRepo, final FeeRepository feeRepo) {
        super();

        personRepository = Objects.requireNonNull(personRepo);
        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Fee fee) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final boolean                existing;
        final Person                 person;

        person = personRepository.findOne(fee.person()
            .number())
            .get();
        existing = feeRepository.exists(person.number(), fee.month());
        if (existing) {
            log.error("Date {} is already registered", fee.month());
            // TODO: this is not a field in the model
            fieldFailure = FieldFailure.of("feeDate", "existing", fee.month());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
