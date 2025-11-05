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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.domain.repository.ContactMethodRepository;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.association.contact.usecase.service.DefaultContactService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Contact service - create")
class TestContactServiceCreate {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ContactRepository       contactRepository;

    @InjectMocks
    private DefaultContactService   service;

    public TestContactServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a contact with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Contact          contact;

        // GIVEN
        contact = Contacts.emptyName();

        // WHEN
        execution = () -> service.create(contact);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("empty", "name.firstName", new ContactName("", "")));
    }

    @Test
    @DisplayName("With a contact with an existing identifier, an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable execution;
        final Contact          contact;

        // GIVEN
        contact = Contacts.toCreate();

        given(contactRepository.existsByIdentifier(ContactConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        execution = () -> service.create(contact);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", ContactConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a contact with an existing identifier, but the identifier is empty, no exception is thrown")
    void testCreate_IdentifierExistsAndEmpty() {
        final Contact contact;

        // GIVEN
        contact = Contacts.toCreateNoIdentifier();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(contact);

        // THEN
        verify(contactRepository).save(Contacts.noIdentifier());
        verify(contactRepository, Mockito.never()).existsByIdentifier(ContactConstants.IDENTIFIER);
    }

    @Test
    @DisplayName("With a contact having padding whitespaces in first and last name, these whitespaces are removed and the contact is persisted")
    void testCreate_Padded_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.padded();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(contact);

        // THEN
        verify(contactRepository).save(Contacts.valid());
    }

    @Test
    @DisplayName("With a valid contact, the contact is persisted")
    void testCreate_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.toCreate();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(contact);

        // THEN
        verify(contactRepository).save(Contacts.valid());
    }

    @Test
    @DisplayName("With a valid contact, the created contact is returned")
    void testCreate_ReturnedData() {
        final Contact contact;
        final Contact created;

        // GIVEN
        contact = Contacts.toCreate();

        given(contactRepository.save(Contacts.valid())).willReturn(Contacts.valid());
        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        created = service.create(contact);

        // THEN
        Assertions.assertThat(created)
            .as("contact")
            .isEqualTo(Contacts.valid());
    }

    @Test
    @DisplayName("With a contact with a not existing contact method, an exception is thrown")
    void testCreate_WithContactChannel_NotExisting() {
        final Contact          contact;
        final ThrowingCallable execution;

        // GIVEN
        contact = Contacts.withEmail();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(contact);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("notExisting", "contactMethod", ContactMethodConstants.NUMBER));
    }

    @Test
    @DisplayName("With a contact with a contact method, the contact is persisted")
    void testCreate_WithContactChannel_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.withEmail();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(contact);

        // THEN
        verify(contactRepository).save(Contacts.withEmail());
    }

}
