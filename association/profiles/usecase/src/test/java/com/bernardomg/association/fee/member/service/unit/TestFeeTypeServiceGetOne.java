
package com.bernardomg.association.fee.member.service.unit;

import static org.mockito.BDDMockito.given;

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
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.usecase.service.DefaultFeeTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeTypeService - getOne")
class TestFeeTypeServiceGetOne {

    @Mock
    private FeeTypeRepository     feeTypeRepository;

    @InjectMocks
    private DefaultFeeTypeService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<FeeType> feeType;

        // GIVEN
        given(feeTypeRepository.findOne(FeeTypeConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));

        // WHEN
        feeType = service.getOne(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .as("fee type")
            .contains(FeeTypes.positive());
    }

    @Test
    @DisplayName("When there is a not existing fee, then an exception is thrown")
    void testGetOne_NotExistingFee() {
        final ThrowingCallable action;

        // GIVEN
        given(feeTypeRepository.findOne(FeeTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing fee, then an exception is thrown")
            .isInstanceOf(MissingFeeTypeException.class);
    }

}
