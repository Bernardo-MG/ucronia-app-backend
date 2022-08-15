
package com.bernardomg.association.memberperiod.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.association.memberperiod.model.MemberPeriod;
import com.bernardomg.association.memberperiod.model.PersistentMemberPeriod;
import com.bernardomg.association.memberperiod.repository.MemberPeriodRepository;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class PeriodNotOverlappedValidationRule implements ValidationRule<MemberPeriod> {

    private final MemberPeriodRepository repository;

    @Override
    public final Collection<ValidationError> test(final MemberPeriod period) {
        final Collection<ValidationError>        result;
        ValidationError                          error;
        final Collection<PersistentMemberPeriod> overlapped;

        // TODO: Move to validator if possible
        overlapped = repository.findOverlapped(period.getMember(), period.getStartMonth(), period.getStartYear(),
            period.getEndMonth(), period.getEndYear());

        result = new ArrayList<>();
        if (!overlapped.isEmpty()) {
            error = ValidationError.of("error.memberPeriod.overlapsExisting");
            result.add(error);
        }

        return result;
    }

}
