
package com.bernardomg.association.fee.member.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.usecase.service.DefaultFeeTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeTypeService - update")
class TestFeeTypeServiceUpdate {

    @Mock
    private FeeTypeRepository     feeTypeRepository;

    @InjectMocks
    private DefaultFeeTypeService service;

    @Test
    @DisplayName("When updating a fee type with a padded name, then the change is persisted")
    void testUpdate_Padded() {
        final FeeType toUpdate;

        // GIVEN
        toUpdate = FeeTypes.padded();

        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(feeTypeRepository.save(toUpdate)).willReturn(FeeTypes.positive());

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(feeTypeRepository).save(FeeTypes.positive());
    }

    @Test
    @DisplayName("When updating a fee, then the change is persisted")
    void testUpdate_PersistedData() {
        final FeeType toUpdate;

        // GIVEN
        toUpdate = FeeTypes.positive();

        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(feeTypeRepository.save(toUpdate)).willReturn(toUpdate);

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(feeTypeRepository).save(toUpdate);
    }

    @Test
    @DisplayName("When updating a fee, then the change is returned")
    void testUpdate_ReturnedData() {
        final FeeType updated;

        // GIVEN
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(feeTypeRepository.save(FeeTypes.positive())).willReturn(FeeTypes.positive());

        // WHEN
        updated = service.update(FeeTypes.positive());

        // THEN
        Assertions.assertThat(updated)
            .as("fee")
            .isEqualTo(FeeTypes.positive());
    }

}
