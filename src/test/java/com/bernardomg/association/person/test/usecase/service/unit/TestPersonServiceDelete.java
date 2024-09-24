/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.person.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.person.usecase.service.DefaultPersonService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Guest service - delete")
class TestPersonServiceDelete {

    @Mock
    private PersonRepository     guestRepository;

    @InjectMocks
    private DefaultPersonService service;

    public TestPersonServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(guestRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.delete(PersonConstants.NUMBER);

        // THEN
        verify(guestRepository).delete(PersonConstants.NUMBER);
    }

    @Test
    @DisplayName("When the guest doesn't exist an exception is thrown")
    void testDelete_NotExisting_NotRemovesEntity() {
        final ThrowingCallable execution;

        // GIVEN
        given(guestRepository.exists(PersonConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.delete(PersonConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPersonException.class);
    }

}
