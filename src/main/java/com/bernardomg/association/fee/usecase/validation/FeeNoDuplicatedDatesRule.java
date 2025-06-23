
package com.bernardomg.association.fee.usecase.validation;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fees's dates are not duplicated.
 */
public final class FeeNoDuplicatedDatesRule implements FieldRule<Collection<Fee>> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(FeeNoDuplicatedDatesRule.class);

    public FeeNoDuplicatedDatesRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Collection<Fee> fees) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final List<YearMonth>        uniqueDates;
        final long                   duplicates;

        uniqueDates = fees.stream()
            .map(Fee::month)
            .distinct()
            .sorted()
            .toList();
        if (uniqueDates.size() < fees.size()) {
            // We have repeated dates
            // TODO: is this really an error? It can be corrected easily
            duplicates = (fees.size() - uniqueDates.size());
            log.error("Received {} fee dates, but {} are duplicates", fees.size(), duplicates);
            // TODO: set duplicates, not number
            fieldFailure = new FieldFailure("duplicated", "feeMonths[]", duplicates);
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
