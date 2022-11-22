
package com.bernardomg.association.test.crud.fee.unit.validation;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.bernardomg.association.crud.fee.model.DtoFeeForm;
import com.bernardomg.association.crud.fee.model.FeeForm;
import com.bernardomg.association.crud.fee.validation.rule.FeeMemberExistsValidationRule;
import com.bernardomg.association.crud.member.repository.MemberRepository;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

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
        final Optional<Failure> failure;
        final DtoFeeForm        fee;
        final FieldFailure      fieldFailure;

        fee = new DtoFeeForm();
        fee.setMemberId(0L);

        failure = validator.test(fee);

        Assertions.assertTrue(failure.isPresent());

        fieldFailure = (FieldFailure) failure.get();
        Assertions.assertEquals("error.member.notExists", fieldFailure.getMessage());
        Assertions.assertEquals("memberId", fieldFailure.getField());
        Assertions.assertEquals("notExists", fieldFailure.getCode());
        Assertions.assertEquals(0L, fieldFailure.getValue());
    }

    @Test
    @DisplayName("Accepts an existing member id")
    public final void testValidator_Valid() throws Exception {
        final Optional<Failure> failure;
        final DtoFeeForm        fee;

        fee = new DtoFeeForm();
        fee.setMemberId(1L);

        failure = validator.test(fee);

        Assertions.assertFalse(failure.isPresent());
    }

}
