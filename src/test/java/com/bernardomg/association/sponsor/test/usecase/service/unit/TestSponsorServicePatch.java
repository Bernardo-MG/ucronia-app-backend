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

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.sponsor.domain.exception.MissingSponsorException;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.association.sponsor.usecase.service.DefaultSponsorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultSponsorService - patch")
class TestSponsorServicePatch {

    @InjectMocks
    private DefaultSponsorService service;

    @Mock
    private SponsorRepository     sponsorRepository;

    public TestSponsorServicePatch() {
        super();
    }

    @Test
    @DisplayName("With a not existing sponsor, an exception is thrown")
    void testPatch_NotExisting_Exception() {
        final Sponsor          sponsor;
        final ThrowingCallable execution;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.patch(sponsor);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingSponsorException.class);
    }

    @Test
    @DisplayName("When patching the name, the change is persisted")
    void testPatch_OnlyName_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.nameChangePatch();

        given(sponsorRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Sponsors.valid()));

        // WHEN
        service.patch(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.nameChange());
    }

    @Test
    @DisplayName("With a sponsor having padding whitespaces in first and last name, these whitespaces are removed")
    void testPatch_Padded_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.padded();

        given(sponsorRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Sponsors.valid()));

        // WHEN
        service.patch(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.valid());
    }

    @Test
    @DisplayName("When updating a sponsor, the change is persisted")
    void testPatch_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Sponsors.valid()));

        // WHEN
        service.patch(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.nameChange());
    }

    @Test
    @DisplayName("When updating a sponsor, the change is returned")
    void testPatch_ReturnedData() {
        final Sponsor sponsor;
        final Sponsor updated;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Sponsors.valid()));
        given(sponsorRepository.save(Sponsors.nameChange())).willReturn(Sponsors.nameChange());

        // WHEN
        updated = service.patch(sponsor);

        // THEN
        Assertions.assertThat(updated)
            .as("sponsor")
            .isEqualTo(Sponsors.nameChange());
    }

}
