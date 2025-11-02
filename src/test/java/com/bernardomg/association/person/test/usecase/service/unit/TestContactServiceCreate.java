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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.model.ContactName;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;
import com.bernardomg.association.person.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.person.test.configuration.factory.Contacts;
import com.bernardomg.association.person.usecase.service.DefaultContactService;
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
    @DisplayName("With a person with an active membership, the person is persisted")
    void testCreate_ActiveMembership_PersistedData() {
        final Contact person;

        // GIVEN
        person = Contacts.membershipActive();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(contactRepository).save(Contacts.membershipActive());
    }

    @Test
    @DisplayName("With a person with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Contact          person;

        // GIVEN
        person = Contacts.emptyName();

        // WHEN
        execution = () -> service.create(person);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("empty", "name.firstName", new ContactName("", "")));
    }

    @Test
    @DisplayName("With a person with an existing identifier, an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable execution;
        final Contact          person;

        // GIVEN
        person = Contacts.toCreate();

        given(contactRepository.existsByIdentifier(ContactConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        execution = () -> service.create(person);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", ContactConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a person with an existing identifier, but the identifier is empty, no exception is thrown")
    void testCreate_IdentifierExistsAndEmpty() {
        final Contact person;

        // GIVEN
        person = Contacts.toCreateNoIdentifier();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(contactRepository).save(Contacts.noIdentifier());
        verify(contactRepository, Mockito.never()).existsByIdentifier(ContactConstants.IDENTIFIER);
    }

    @Test
    @DisplayName("With a person with an inactive membership, the person is persisted")
    void testCreate_InactiveMembership_PersistedData() {
        final Contact person;

        // GIVEN
        person = Contacts.membershipInactive();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(contactRepository).save(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("With a person with no membership, the person is persisted")
    void testCreate_NoMembership_PersistedData() {
        final Contact person;

        // GIVEN
        person = Contacts.noMembership();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(contactRepository).save(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a person having padding whitespaces in first and last name, these whitespaces are removed and the person is persisted")
    void testCreate_Padded_PersistedData() {
        final Contact person;

        // GIVEN
        person = Contacts.padded();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(contactRepository).save(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a valid person, the person is persisted")
    void testCreate_PersistedData() {
        final Contact person;

        // GIVEN
        person = Contacts.toCreate();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(contactRepository).save(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a valid person, the created person is returned")
    void testCreate_ReturnedData() {
        final Contact person;
        final Contact created;

        // GIVEN
        person = Contacts.toCreate();

        given(contactRepository.save(Contacts.noMembership())).willReturn(Contacts.noMembership());
        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);

        // WHEN
        created = service.create(person);

        // THEN
        Assertions.assertThat(created)
            .as("person")
            .isEqualTo(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a person with a not existing contact method, an exception is thrown")
    void testCreate_WithContact_NotExisting() {
        final Contact          person;
        final ThrowingCallable execution;

        // GIVEN
        person = Contacts.withEmail();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(person);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("notExisting", "contact", ContactMethodConstants.NUMBER));
    }

    @Test
    @DisplayName("With a person with a contact method, the person is persisted")
    void testCreate_WithContact_PersistedData() {
        final Contact person;

        // GIVEN
        person = Contacts.withEmail();

        given(contactRepository.findNextNumber()).willReturn(ContactConstants.NUMBER);
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(person);

        // THEN
        verify(contactRepository).save(Contacts.withEmail());
    }

}
