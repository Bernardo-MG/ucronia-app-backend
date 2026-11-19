
package com.bernardomg.association.sponsor.test.service.unit;

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
@DisplayName("DefaultProfileSponsorshipService - convertToSponsor")
class TestProfileSponsorshipServiceConvert {

    @Mock
    private ProfileRepository                profileRepository;

    @InjectMocks
    private DefaultProfileSponsorshipService service;

    @Mock
    private SponsorRepository                sponsorRepository;

    @Test
    @DisplayName("When there is an existing sponsor, then an exception is thrown")
    void testConvertToSponsor_ExistingSponsor_Exception() {
        final ThrowingCallable action;
        final Profile          profile;

        // GIVEN
        Sponsors.nameChange();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is an existing sponsor, then an exception is thrown")
            .isInstanceOf(SponsorExistsException.class);
    }

    @Test
    @DisplayName("When there is a not existing profile, then an exception is thrown")
    void testConvertToSponsor_NotExistingProfile_Exception() {
        final ThrowingCallable action;

        // GIVEN
        Sponsors.nameChange();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing profile, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When converting to sponsor, then the change is persisted")
    void testConvertToSponsor_PersistedData() {
        final Sponsor sponsor;
        final Profile profile;

        // GIVEN
        sponsor = Sponsors.withoutYear();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        verify(sponsorRepository).save(sponsor);
    }

    @Test
    @DisplayName("When converting to sponsor, then the change is returned")
    void testConvertToSponsor_ReturnedData() {
        final Sponsor sponsor;
        final Profile profile;
        final Sponsor updated;

        // GIVEN
        sponsor = Sponsors.withoutYear();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(false);
        given(sponsorRepository.save(sponsor)).willReturn(sponsor);

        // WHEN
        updated = service.convertToSponsor(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("sponsor")
            .isEqualTo(sponsor);
    }

}
