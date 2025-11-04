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

package com.bernardomg.association.contact.test.usecase.service.unit;

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

import com.bernardomg.association.contact.domain.model.ContactMethod;
import com.bernardomg.association.contact.domain.repository.ContactMethodRepository;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethods;
import com.bernardomg.association.contact.usecase.service.DefaultContactMethodService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Contact method service - create")
class TestContactMethodServiceCreate {

    @Mock
    private ContactMethodRepository     ContactMethodRepository;

    @InjectMocks
    private DefaultContactMethodService service;

    public TestContactMethodServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a contact method with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final ContactMethod    contactMethod;

        // GIVEN
        contactMethod = ContactMethods.emptyName();

        // WHEN
        execution = () -> service.create(contactMethod);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("With a contact method with an existing name, an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable execution;
        final ContactMethod    contactMethod;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.existsByName(ContactMethodConstants.EMAIL)).willReturn(true);

        // WHEN
        execution = () -> service.create(contactMethod);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "name", ContactMethodConstants.EMAIL));
    }

    @Test
    @DisplayName("With a valid contact method, the contact method is persisted")
    void testCreate_PersistedData() {
        final ContactMethod contactMethod;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.findNextNumber()).willReturn(ContactMethodConstants.NUMBER);

        // WHEN
        service.create(contactMethod);

        // THEN
        verify(ContactMethodRepository).save(ContactMethods.email());
    }

    @Test
    @DisplayName("With a valid contact method, the created contact method is returned")
    void testCreate_ReturnedData() {
        final ContactMethod contactMethod;
        final ContactMethod created;

        // GIVEN
        contactMethod = ContactMethods.email();

        given(ContactMethodRepository.save(ContactMethods.email())).willReturn(ContactMethods.email());
        given(ContactMethodRepository.findNextNumber()).willReturn(ContactMethodConstants.NUMBER);

        // WHEN
        created = service.create(contactMethod);

        // THEN
        Assertions.assertThat(created)
            .as("contact method")
            .isEqualTo(ContactMethods.email());
    }

}
