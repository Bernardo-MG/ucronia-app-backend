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

package com.bernardomg.activity.test.service.unit;

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

import com.bernardomg.association.activity.domain.exception.MissingActivityException;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.activity.test.configuration.factory.Activities;
import com.bernardomg.association.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.activity.usecase.service.DefaultActivityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Activity service - delete")
class TestActivityServiceDelete {

    @Mock
    private ActivityRepository     ActivityRepository;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("When the Activity doesn't exist, an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(ActivityRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingActivityException.class);
    }

    @Test
    @DisplayName("When deleting the repository is called")
    void testDelete_RemovesEntity() {
        // GIVEN
        given(ActivityRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(Activities.valid()));

        // WHEN
        service.delete(ActivityConstants.NUMBER);

        // THEN
        verify(ActivityRepository).delete(ActivityConstants.NUMBER);
    }

}
