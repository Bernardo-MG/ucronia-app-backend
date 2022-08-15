
package com.bernardomg.association.paidmonth.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.association.memberperiod.model.PersistentMemberPeriod;
import com.bernardomg.association.memberperiod.repository.MemberPeriodRepository;
import com.bernardomg.association.paidmonth.model.PaidMonth;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class PaidMonthContainedInPeriodValidationRule implements ValidationRule<PaidMonth> {

    private final MemberPeriodRepository periodRepository;

    @Override
    public final Collection<ValidationError> test(final PaidMonth month) {
        final Collection<ValidationError>        result;
        final ValidationError                    error;
        final Collection<PersistentMemberPeriod> containing;

        containing = periodRepository.findContaining(month.getMember(), month.getMonth(), month.getYear());

        result = new ArrayList<>();
        if (containing.isEmpty()) {
            error = ValidationError.of("error.paidMonth.outOfPeriod");
            result.add(error);
        }

        return result;
    }

}
