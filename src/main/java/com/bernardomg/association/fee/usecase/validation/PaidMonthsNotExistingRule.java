
package com.bernardomg.association.fee.usecase.validation;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fee's months are not registered. There is an special case, as dates for unpaid fees are ignored, to allow
 * paying those dates.
 */
public final class PaidMonthsNotExistingRule implements FieldRule<Collection<Fee>> {

    /**
     * Logger for the class.
     */
    private static final Logger    log = LoggerFactory.getLogger(PaidMonthsNotExistingRule.class);

    private final FeeRepository    feeRepository;

    private final PersonRepository personRepository;

    public PaidMonthsNotExistingRule(final PersonRepository personRepo, final FeeRepository feeRepo) {
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
                .member()
                .number();
            person = personRepository.findOne(number)
                .get();
            // TODO: use a single query
            existing = fees.stream()
                .map(Fee::month)
                .filter(date -> feeRepository.existsPaid(person.number(), date))
                .toList();
            if (!existing.isEmpty()) {
                log.error("Dates {} are already registered", existing);
                // TODO: this is not a field in the model
                fieldFailure = new FieldFailure("existing", "months[]", existing);
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
