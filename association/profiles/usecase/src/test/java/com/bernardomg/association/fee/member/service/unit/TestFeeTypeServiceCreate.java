
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
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.usecase.service.DefaultFeeTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeTypeService - create")
class TestFeeTypeServiceCreate {

    @Mock
    private FeeTypeRepository     feeTypeRepository;

    @InjectMocks
    private DefaultFeeTypeService service;

    @Test
    @DisplayName("When there is a padded name, then the fee type is persisted")
    void testCreate_Padded_PersistedData() {
        final FeeType feeType;

        // GIVEN
        feeType = FeeTypes.padded();

        // WHEN
        service.create(feeType);

        // THEN
        verify(feeTypeRepository).save(FeeTypes.toCreate());
    }

    @Test
    @DisplayName("When there is a valid fee type, then it is persisted")
    void testCreate_PersistedData() {
        final FeeType feeType;

        // GIVEN
        feeType = FeeTypes.positive();

        // WHEN
        service.create(feeType);

        // THEN
        verify(feeTypeRepository).save(FeeTypes.toCreate());
    }

    @Test
    @DisplayName("When there is a valid fee type, then it is returned")
    void testCreate_ReturnedData() {
        final FeeType feeType;
        final FeeType created;

        // GIVEN
        feeType = FeeTypes.positive();

        given(feeTypeRepository.save(FeeTypes.toCreate())).willReturn(FeeTypes.positive());

        // WHEN
        created = service.create(feeType);

        // THEN
        Assertions.assertThat(created)
            .as("feeType")
            .isEqualTo(FeeTypes.positive());
    }

}
