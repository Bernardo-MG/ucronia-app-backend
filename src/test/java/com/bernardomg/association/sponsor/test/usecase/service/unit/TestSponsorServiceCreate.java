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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.association.sponsor.usecase.service.DefaultSponsorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultSponsorService - create")
class TestSponsorServiceCreate {

    @InjectMocks
    private DefaultSponsorService service;

    @Mock
    private SponsorRepository     sponsorRepository;

    public TestSponsorServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a sponsor having padding whitespaces in first and last name, these whitespaces are removed and the contact is persisted")
    void testCreate_Padded_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.padded();

        // WHEN
        service.create(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.toCreate());
    }

    @Test
    @DisplayName("With a valid sponsor, the contact is persisted")
    void testCreate_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.toCreate();

        // WHEN
        service.create(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.toCreate());
    }

    @Test
    @DisplayName("With a valid sponsor, the created contact is returned")
    void testCreate_ReturnedData() {
        final Sponsor sponsor;
        final Sponsor created;

        // GIVEN
        sponsor = Sponsors.toCreate();

        given(sponsorRepository.save(sponsor)).willReturn(Sponsors.valid());

        // WHEN
        created = service.create(sponsor);

        // THEN
        Assertions.assertThat(created)
            .as("contact")
            .isEqualTo(Sponsors.valid());
    }

}
