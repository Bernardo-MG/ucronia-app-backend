
package com.bernardomg.association.crud.fee.validation.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bernardomg.association.crud.fee.model.FeeForm;
import com.bernardomg.association.crud.member.repository.MemberRepository;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class FeeMemberExistsValidationRule implements ValidationRule<FeeForm> {

    private final MemberRepository repository;

    @Override
    public final Optional<Failure> test(final FeeForm form) {
        final Failure           failure;
        final Optional<Failure> result;

        if (!repository.existsById(form.getMemberId())) {
            failure = FieldFailure.of("error.member.notExists", "memberId", "notExists", form.getMemberId());
            result = Optional.of(failure);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final String toString() {
        return this.getClass()
            .getName();
    }

}
