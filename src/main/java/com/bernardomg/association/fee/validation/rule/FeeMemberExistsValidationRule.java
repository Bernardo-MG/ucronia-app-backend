
package com.bernardomg.association.fee.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.model.Fee;
import com.bernardomg.association.member.repository.MemberRepository;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class FeeMemberExistsValidationRule implements ValidationRule<Fee> {

    private final MemberRepository repository;

    @Override
    public final Collection<ValidationError> test(final Fee period) {
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
