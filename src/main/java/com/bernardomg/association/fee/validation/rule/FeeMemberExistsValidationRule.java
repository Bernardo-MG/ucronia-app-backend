
package com.bernardomg.association.fee.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.association.member.repository.MemberRepository;
import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class FeeMemberExistsValidationRule implements ValidationRule<FeeForm> {

    private final MemberRepository repository;

    @Override
    public final Collection<Failure> test(final FeeForm period) {
        final Collection<Failure> result;
        final Failure             error;

        result = new ArrayList<>();
        if (!repository.existsById(period.getMemberId())) {
            error = Failure.of("error.member.notExists");
            result.add(error);
        }

        return result;
    }

    @Override
    public final String toString() {
        return this.getClass()
            .getName();
    }

}
