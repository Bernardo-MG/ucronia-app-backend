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

package com.bernardomg.association.sponsor.test.usecase.service.unit;

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
import com.bernardomg.association.sponsor.domain.exception.SponsorExistsException;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.association.sponsor.usecase.service.DefaultContactSponsorshipService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultContactSponsorshipService - convert to sponsor")
class TestContactSponsorshipServiceConvert {

    @Mock
    private ContactRepository                contactRepository;

    @InjectMocks
    private DefaultContactSponsorshipService service;

    @Mock
    private SponsorRepository                sponsorRepository;

    public TestContactSponsorshipServiceConvert() {
        super();
    }

    @Test
    @DisplayName("With an existing sponsor, an exception is thrown")
    void testConvertToSponsor_ExistingSponsor_Exception() {
        final ThrowingCallable execution;
        final Contact          contact;

        // GIVEN
        Sponsors.nameChange();
        contact = Contacts.valid();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(contact));
        given(sponsorRepository.exists(ContactConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.convertToSponsor(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(SponsorExistsException.class);
    }

    @Test
    @DisplayName("With a not existing contact, an exception is thrown")
    void testConvertToSponsor_NotExistingContact_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        Sponsors.nameChange();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.convertToSponsor(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactException.class);
    }

    @Test
    @DisplayName("When converting to sponsor, the change is persisted")
    void testConvertToSponsor_PersistedData() {
        final Sponsor sponsor;
        final Contact contact;

        // GIVEN
        sponsor = Sponsors.noYears();
        contact = Contacts.valid();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(contact));
        given(sponsorRepository.exists(ContactConstants.NUMBER)).willReturn(false);

        // WHEN
        service.convertToSponsor(ContactConstants.NUMBER);

        // THEN
        verify(sponsorRepository).save(sponsor, ContactConstants.NUMBER);
    }

    @Test
    @DisplayName("When converting to sponsor, the change is returned")
    void testConvertToSponsor_ReturnedData() {
        final Sponsor sponsor;
        final Contact contact;
        final Sponsor updated;

        // GIVEN
        sponsor = Sponsors.noYears();
        contact = Contacts.valid();

        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(contact));
        given(sponsorRepository.exists(ContactConstants.NUMBER)).willReturn(false);
        given(sponsorRepository.save(sponsor, ContactConstants.NUMBER)).willReturn(sponsor);

        // WHEN
        updated = service.convertToSponsor(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("sponsor")
            .isEqualTo(sponsor);
    }

}
