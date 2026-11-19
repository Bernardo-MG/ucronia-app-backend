
package com.bernardomg.association.fee.member.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.usecase.service.DefaultFeeTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeTypeService - delete")
class TestFeeTypeServiceDelete {

    @Mock
    private FeeTypeRepository     feeTypeRepository;

    @InjectMocks
    private DefaultFeeTypeService service;

    @Test
    @DisplayName("When deleting the a fee type, then it is removed")
    void testDelete() {
        // GIVEN
        given(feeTypeRepository.findOne(FeeTypeConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));

        // WHEN
        service.delete(FeeTypeConstants.NUMBER);

        // THEN
        verify(feeTypeRepository).delete(FeeTypeConstants.NUMBER);
    }

    @Test
    @DisplayName("When there is a not existing fee, then an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(feeTypeRepository.findOne(FeeTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing fee, then an exception is thrown")
            .isInstanceOf(MissingFeeTypeException.class);
    }

}
