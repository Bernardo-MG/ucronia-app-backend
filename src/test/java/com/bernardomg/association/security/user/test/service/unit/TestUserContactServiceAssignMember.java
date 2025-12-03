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

package com.bernardomg.association.security.user.test.service.unit;

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

import com.bernardomg.association.contact.domain.exception.MissingContactException;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.Users;
import com.bernardomg.association.security.user.usecase.service.DefaultUserContactService;
import com.bernardomg.security.user.domain.exception.MissingUsernameException;
import com.bernardomg.security.user.domain.repository.UserRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserContactService - assign contact")
class TestUserContactServiceAssignContact {

    @Mock
    private ContactRepository         contactRepository;

    @InjectMocks
    private DefaultUserContactService service;

    @Mock
    private UserContactRepository     userContactRepository;

    @Mock
    private UserRepository            userRepository;

    @Test
    @DisplayName("When the contact has already been assigned, it throws an exception")
    void testAssignContact_ExistingContact() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.valid()));

        given(userContactRepository.existsByContactForAnotherUser(UserConstants.USERNAME, ContactConstants.NUMBER))
            .willReturn(true);

        // WHEN
        execution = () -> service.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "contact", ContactConstants.NUMBER));
    }

    @Test
    @DisplayName("When the user already has a contact, it throws an exception")
    void testAssignContact_ExistingUser() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.valid()));

        given(userContactRepository.existsByContactForAnotherUser(UserConstants.USERNAME, ContactConstants.NUMBER))
            .willReturn(true);

        // WHEN
        execution = () -> service.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "contact", ContactConstants.NUMBER));
    }

    @Test
    @DisplayName("With no contact, it throws an exception")
    void testAssignContact_NoContact() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.empty());

        // TODO: assign when the user already has a person

        // WHEN
        execution = () -> service.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactException.class);
    }

    @Test
    @DisplayName("With no user, it throws an exception")
    void testAssignContact_NoUser() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingUsernameException.class);
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    void testAssignContact_PersistedData() {

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.valid()));

        // WHEN
        service.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        verify(userContactRepository).assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    void testAssignContact_ReturnedData() {
        final Contact contact;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.valid()));
        given(userContactRepository.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER))
            .willReturn(Contacts.valid());

        // WHEN
        contact = service.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .isEqualTo(Contacts.valid());
    }

}
