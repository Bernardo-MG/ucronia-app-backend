
package com.bernardomg.association.fee.usecase.validation;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the fees's dates are not duplicated.
 */
@Slf4j
public final class FeeNoDuplicatedDatesRule implements FieldRule<Collection<Fee>> {

    public FeeNoDuplicatedDatesRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Collection<Fee> fees) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final long                   uniqueDates;
        final int                    totalDates;
        final long                   duplicates;

        uniqueDates = fees.stream()
            .map(Fee::getDate)
            .distinct()
            .count();
        totalDates = fees.size();
        if (uniqueDates < totalDates) {
            // TODO: is this really an error? It can be corrected easily
            duplicates = (totalDates - uniqueDates);
            log.error("Received {} fee dates, but {} are duplicates", totalDates, duplicates);
            fieldFailure = FieldFailure.of("feeDates[]", "duplicated", duplicates);
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
