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
@DisplayName("DefaultSponsorService - update")
class TestSponsorServiceUpdate {

    @InjectMocks
    private DefaultSponsorService service;

    @Mock
    private SponsorRepository     sponsorRepository;

    public TestSponsorServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With a not existing sponsor, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Sponsor          sponsor;
        final ThrowingCallable execution;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.exists(ContactConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(sponsor);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingSponsorException.class);
    }

    @Test
    @DisplayName("With a sponsor having padding whitespaces in first and last name, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.padded();

        given(sponsorRepository.exists(ContactConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.valid());
    }

    @Test
    @DisplayName("When updating a sponsor, the change is persisted")
    void testUpdate_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.exists(ContactConstants.NUMBER)).willReturn(true);
        given(sponsorRepository.save(Sponsors.nameChange())).willReturn(Sponsors.nameChange());

        // WHEN
        service.update(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.nameChange());
    }

    @Test
    @DisplayName("When updating a sponsor, the change is returned")
    void testUpdate_ReturnedData() {
        final Sponsor sponsor;
        final Sponsor updated;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.exists(ContactConstants.NUMBER)).willReturn(true);
        given(sponsorRepository.save(Sponsors.nameChange())).willReturn(Sponsors.nameChange());

        // WHEN
        updated = service.update(sponsor);

        // THEN
        Assertions.assertThat(updated)
            .as("sponsor")
            .isEqualTo(Sponsors.nameChange());
    }

}
