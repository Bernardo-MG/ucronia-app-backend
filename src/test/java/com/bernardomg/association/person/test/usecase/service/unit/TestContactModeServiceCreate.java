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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.model.ContactMode;
import com.bernardomg.association.person.domain.repository.ContactModeRepository;
import com.bernardomg.association.person.test.configuration.factory.ContactModeConstants;
import com.bernardomg.association.person.test.configuration.factory.ContactModes;
import com.bernardomg.association.person.usecase.service.DefaultContactModeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Contact mode service - create")
class TestContactModeServiceCreate {

    @Mock
    private ContactModeRepository     contactModeRepository;

    @InjectMocks
    private DefaultContactModeService service;

    public TestContactModeServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a valid contact mode, the contact mode is persisted")
    void testCreate_PersistedData() {
        final ContactMode contactMode;

        // GIVEN
        contactMode = ContactModes.valid();

        given(contactModeRepository.findNextNumber()).willReturn(ContactModeConstants.NUMBER);

        // WHEN
        service.create(contactMode);

        // THEN
        verify(contactModeRepository).save(ContactModes.valid());
    }

    @Test
    @DisplayName("With a valid contact mode, the created contact mode is returned")
    void testCreate_ReturnedData() {
        final ContactMode contactMode;
        final ContactMode created;

        // GIVEN
        contactMode = ContactModes.valid();

        given(contactModeRepository.save(ContactModes.valid())).willReturn(ContactModes.valid());
        given(contactModeRepository.findNextNumber()).willReturn(ContactModeConstants.NUMBER);

        // WHEN
        created = service.create(contactMode);

        // THEN
        Assertions.assertThat(created)
            .as("contact mode")
            .isEqualTo(ContactModes.valid());
    }

}
