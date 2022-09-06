
package com.bernardomg.association.test.fee.unit.validation;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.bernardomg.association.fee.model.DtoFeeForm;
import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.association.fee.validation.rule.FeeMemberExistsValidationRule;
import com.bernardomg.association.member.repository.MemberRepository;
import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.validation.ValidationRule;

@DisplayName("Fee member exists validation rule")
public class TestFeeMemberExistsValidationRule {

    private final ValidationRule<FeeForm> validator;

    public TestFeeMemberExistsValidationRule() {
        super();
        final MemberRepository repository;

        repository = Mockito.mock(MemberRepository.class);
        Mockito.when(repository.existsById(ArgumentMatchers.anyLong()))
        .thenReturn(false);
        Mockito.when(repository.existsById(ArgumentMatchers.eq(1L)))
        .thenReturn(true);

        validator = new FeeMemberExistsValidationRule(repository);
    }

    @Test
    @DisplayName("Rejects the member id when it doesn't exist")
    public final void testValidator_MemberNotExists() throws Exception {
        final Collection<Failure> failures;
        final DtoFeeForm          fee;
        final FieldFailure        failure;

        fee = new DtoFeeForm();
        fee.setMemberId(0L);

        failures = validator.test(fee);

        Assertions.assertEquals(1, failures.size());

        failure = (FieldFailure) failures.iterator()
            .next();
        Assertions.assertEquals("error.member.notExists", failure.getMessage());
        Assertions.assertEquals("feeForm", failure.getObject());
        Assertions.assertEquals("memberId", failure.getField());
        Assertions.assertEquals(0L, failure.getValue());
    }

    @Test
    @DisplayName("Accepts an existing member id")
    public final void testValidator_Valid() throws Exception {
        final Collection<Failure> failures;
        final DtoFeeForm          fee;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);

        failures = validator.test(fee);

        Assertions.assertEquals(0, failures.size());
    }

}
