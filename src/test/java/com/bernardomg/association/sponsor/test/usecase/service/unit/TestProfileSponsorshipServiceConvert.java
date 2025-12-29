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

import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.sponsor.domain.exception.SponsorExistsException;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.association.sponsor.usecase.service.DefaultProfileSponsorshipService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultProfileSponsorshipService - convert to sponsor")
class TestProfileSponsorshipServiceConvert {

    @Mock
    private ProfileRepository                profileRepository;

    @InjectMocks
    private DefaultProfileSponsorshipService service;

    @Mock
    private SponsorRepository                sponsorRepository;

    public TestProfileSponsorshipServiceConvert() {
        super();
    }

    @Test
    @DisplayName("With an existing sponsor, an exception is thrown")
    void testConvertToSponsor_ExistingSponsor_Exception() {
        final ThrowingCallable execution;
        final Profile          profile;

        // GIVEN
        Sponsors.nameChange();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(SponsorExistsException.class);
    }

    @Test
    @DisplayName("With a not existing profile, an exception is thrown")
    void testConvertToSponsor_NotExistingContact_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        Sponsors.nameChange();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When converting to sponsor, the change is persisted")
    void testConvertToSponsor_PersistedData() {
        final Sponsor sponsor;
        final Profile profile;

        // GIVEN
        sponsor = Sponsors.toConvert();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        verify(sponsorRepository).save(sponsor, ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When converting to sponsor, the change is returned")
    void testConvertToSponsor_ReturnedData() {
        final Sponsor sponsor;
        final Profile profile;
        final Sponsor updated;

        // GIVEN
        sponsor = Sponsors.toConvert();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(false);
        given(sponsorRepository.save(sponsor, ProfileConstants.NUMBER)).willReturn(sponsor);

        // WHEN
        updated = service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("sponsor")
            .isEqualTo(sponsor);
    }

}
