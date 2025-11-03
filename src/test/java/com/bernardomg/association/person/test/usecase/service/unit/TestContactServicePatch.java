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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.exception.MissingContactException;
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
@DisplayName("Contact service - patch")
class TestContactServicePatch {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ContactRepository       contactRepository;

    @InjectMocks
    private DefaultContactService   service;

    public TestContactServicePatch() {
        super();
    }

    @Test
    @DisplayName("With a contact with an existing identifier, an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable execution;
        final Contact          contact;

        // GIVEN
        contact = Contacts.nameChange();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));
        given(contactRepository.existsByIdentifierForAnother(ContactConstants.NUMBER, ContactConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        execution = () -> service.patch(contact);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", ContactConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a contact with an existing identifier, but the identifier is empty, no exception is thrown")
    void testCreate_IdentifierExistsAndEmpty() {
        final Contact contact;

        // GIVEN
        contact = Contacts.noIdentifier();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));

        // WHEN
        service.patch(contact);

        // THEN
        verify(contactRepository).save(Contacts.noIdentifier());
        verify(contactRepository, Mockito.never()).existsByIdentifierForAnother(ContactConstants.NUMBER,
            ContactConstants.IDENTIFIER);
    }

    @Test
    @DisplayName("With a contact with an empty name, an exception is thrown")
    void testPatch_EmptyName() {
        final ThrowingCallable execution;
        final Contact          contact;

        // GIVEN
        contact = Contacts.emptyName();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));

        // WHEN
        execution = () -> service.patch(contact);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("empty", "name.firstName", new ContactName("", "")));
    }

    @Test
    @DisplayName("When disabling a contact, the change is persisted")
    void testPatch_Inactive_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.membershipInactive();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));

        // WHEN
        service.patch(contact);

        // THEN
        verify(contactRepository).save(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("When disabling a contact, the change is returned")
    void testPatch_Inactive_ReturnedData() {
        final Contact contact;
        final Contact updated;

        // GIVEN
        contact = Contacts.membershipInactive();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));
        given(contactRepository.save(Contacts.membershipInactive())).willReturn(Contacts.membershipInactive());

        // WHEN
        updated = service.patch(contact);

        // THEN
        Assertions.assertThat(updated)
            .as("contact")
            .isEqualTo(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("With a not existing contact, an exception is thrown")
    void testPatch_NotExisting_Exception() {
        final Contact          contact;
        final ThrowingCallable execution;

        // GIVEN
        contact = Contacts.nameChange();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.patch(contact);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactException.class);
    }

    @Test
    @DisplayName("When patching the name, the change is persisted")
    void testPatch_OnlyName_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.nameChangePatch();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));

        // WHEN
        service.patch(contact);

        // THEN
        verify(contactRepository).save(Contacts.nameChange());
    }

    @Test
    @DisplayName("With a contact having padding whitespaces in first and last name, these whitespaces are removed")
    void testPatch_Padded_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.padded();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));

        // WHEN
        service.patch(contact);

        // THEN
        verify(contactRepository).save(Contacts.noMembership());
    }

    @Test
    @DisplayName("When updating a contact, the change is persisted")
    void testPatch_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.nameChange();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));

        // WHEN
        service.patch(contact);

        // THEN
        verify(contactRepository).save(Contacts.nameChange());
    }

    @Test
    @DisplayName("When updating a contact, the change is returned")
    void testPatch_ReturnedData() {
        final Contact contact;
        final Contact updated;

        // GIVEN
        contact = Contacts.nameChange();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));
        given(contactRepository.save(Contacts.nameChange())).willReturn(Contacts.nameChange());

        // WHEN
        updated = service.patch(contact);

        // THEN
        Assertions.assertThat(updated)
            .as("contact")
            .isEqualTo(Contacts.nameChange());
    }

    @Test
    @DisplayName("With a contact with a not existing contact method, an exception is thrown")
    void testPatch_WithContact_NotExisting() {
        final Contact          contact;
        final ThrowingCallable execution;

        // GIVEN
        contact = Contacts.withEmail();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.patch(contact);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("notExisting", "contact", ContactMethodConstants.NUMBER));
    }

    @Test
    @DisplayName("When patching a contact with a contact method, the change is persisted")
    void testPatch_WithContact_PersistedData() {
        final Contact contact;

        // GIVEN
        contact = Contacts.withEmail();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(contact);

        // THEN
        verify(contactRepository).save(Contacts.withEmail());
    }

}
