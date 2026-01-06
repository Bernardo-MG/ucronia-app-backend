/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.fee.test.usecase.service.unit;

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
@DisplayName("Fee type service - create")
class TestFeeTypeServiceCreate {

    @Mock
    private FeeTypeRepository     feeTypeRepository;

    @InjectMocks
    private DefaultFeeTypeService service;

    @Test
    @DisplayName("With a padded name, the fee type is persisted")
    void testCreate_Padded_PersistedData() {
        final FeeType feeType;

        // GIVEN
        feeType = FeeTypes.padded();

        // WHEN
        service.create(feeType);

        // THEN
        verify(feeTypeRepository).save(FeeTypes.created());
    }

    @Test
    @DisplayName("With a valid fee type, it is persisted")
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
    @DisplayName("With a valid fee type, it is returned")
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
