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

import com.bernardomg.association.person.domain.exception.MissingContactModeException;
import com.bernardomg.association.person.domain.repository.ContactModeRepository;
import com.bernardomg.association.person.test.configuration.factory.ContactModeConstants;
import com.bernardomg.association.person.usecase.service.DefaultContactModeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Contact mode service - delete")
class TestContactModeServiceDelete {

    @Mock
    private ContactModeRepository     contactModeRepository;

    @InjectMocks
    private DefaultContactModeService service;

    public TestContactModeServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(contactModeRepository.exists(ContactModeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.delete(ContactModeConstants.NUMBER);

        // THEN
        verify(contactModeRepository).delete(ContactModeConstants.NUMBER);
    }

    @Test
    @DisplayName("When the contact mode doesn't exist an exception is thrown")
    void testDelete_NotExisting_NotRemovesEntity() {
        final ThrowingCallable execution;

        // GIVEN
        given(contactModeRepository.exists(ContactModeConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.delete(ContactModeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactModeException.class);
    }

}
