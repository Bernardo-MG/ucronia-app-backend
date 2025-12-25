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

package com.bernardomg.association.guest.test.usecase.service.unit;

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
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.domain.exception.GuestExistsException;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.usecase.service.DefaultContactGuestService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultContactGuestService - convert to guest")
class TestContactGuestServiceConvert {

    @Mock
    private ContactRepository          contactRepository;

    @Mock
    private GuestRepository            guestRepository;

    @InjectMocks
    private DefaultContactGuestService service;

    public TestContactGuestServiceConvert() {
        super();
    }

    @Test
    @DisplayName("With an existing guest, an exception is thrown")
    void testConvertToGuest_ExistingGuest_Exception() {
        final ThrowingCallable execution;
        final Contact          contact;

        // GIVEN
        Guests.nameChange();
        contact = Contacts.valid();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(contact));
        given(guestRepository.exists(ContactConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.convertToGuest(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(GuestExistsException.class);
    }

    @Test
    @DisplayName("With a not existing contact, an exception is thrown")
    void testConvertToGuest_NotExistingContact_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        Guests.nameChange();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.convertToGuest(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactException.class);
    }

    @Test
    @DisplayName("When converting to guest, the change is persisted")
    void testConvertToGuest_PersistedData() {
        final Guest   guest;
        final Contact contact;

        // GIVEN
        guest = Guests.noGames();
        contact = Contacts.withType(GuestEntityConstants.CONTACT_TYPE);

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(contact));
        given(guestRepository.exists(ContactConstants.NUMBER)).willReturn(false);

        // WHEN
        service.convertToGuest(ContactConstants.NUMBER);

        // THEN
        verify(guestRepository).save(guest, ContactConstants.NUMBER);
    }

    @Test
    @DisplayName("When converting to guest, the change is returned")
    void testConvertToGuest_ReturnedData() {
        final Guest   guest;
        final Contact contact;
        final Guest   updated;

        // GIVEN
        guest = Guests.noGames();
        contact = Contacts.withType(GuestEntityConstants.CONTACT_TYPE);

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(contact));
        given(guestRepository.exists(ContactConstants.NUMBER)).willReturn(false);
        given(guestRepository.save(guest, ContactConstants.NUMBER)).willReturn(guest);

        // WHEN
        updated = service.convertToGuest(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("guest")
            .isEqualTo(guest);
    }

}
