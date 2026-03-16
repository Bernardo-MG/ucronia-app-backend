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
@DisplayName("Fee type service - delete")
class TestFeeTypeServiceDelete {

    @Mock
    private FeeTypeRepository     feeTypeRepository;

    @InjectMocks
    private DefaultFeeTypeService service;

    public TestFeeTypeServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting the a fee type, it is removed")
    void testDelete() {
        // GIVEN
        given(feeTypeRepository.findOne(FeeTypeConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));

        // WHEN
        service.delete(FeeTypeConstants.NUMBER);

        // THEN
        verify(feeTypeRepository).delete(FeeTypeConstants.NUMBER);
    }

    @Test
    @DisplayName("With a not existing fee, an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(feeTypeRepository.findOne(FeeTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(FeeTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeTypeException.class);
    }

}
