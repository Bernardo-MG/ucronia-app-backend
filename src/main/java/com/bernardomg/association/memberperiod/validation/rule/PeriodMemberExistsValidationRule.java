
package com.bernardomg.association.memberperiod.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.association.member.repository.MemberRepository;
import com.bernardomg.association.memberperiod.model.MemberPeriod;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class PeriodMemberExistsValidationRule implements ValidationRule<MemberPeriod> {

    private final MemberRepository repository;

    @Override
    public final Collection<ValidationError> test(final MemberPeriod period) {
        final Collection<ValidationError> result;
        final ValidationError             error;

        result = new ArrayList<>();
        if (!repository.existsById(period.getMember())) {
            error = ValidationError.of("error.member.notExists");
            result.add(error);
        }

        return result;
    }

}
