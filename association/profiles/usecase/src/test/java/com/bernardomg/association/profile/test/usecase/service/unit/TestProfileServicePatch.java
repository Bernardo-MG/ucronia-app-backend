
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.profile.usecase.service.DefaultProfileService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultProfileService - patch")
class TestProfileServicePatch {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ProfileRepository       profileRepository;

    @InjectMocks
    private DefaultProfileService   service;

    @Test
    @DisplayName("When there is a profile with an existing identifier, then an exception is thrown")
    void testPatch_IdentifierExists() {
        final ThrowingCallable action;
        final Profile          profile;

        // GIVEN
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(profileRepository.existsByIdentifierForAnother(ProfileConstants.NUMBER, ProfileConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        action = () -> service.patch(profile);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a not existing profile, then an exception is thrown")
    void testPatch_NotExisting_Exception() {
        final Profile          profile;
        final ThrowingCallable action;

        // GIVEN
        profile = Profiles.nameChange();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.patch(profile);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing profile, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When patching the name, then the change is persisted")
    void testPatch_OnlyName_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.nameChangePatch();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));

        // WHEN
        service.patch(profile);

        // THEN
        verify(profileRepository).save(Profiles.nameChangePatch());
    }

    @Test
    @DisplayName("When there is a profile having padding whitespaces in first and last name, then these whitespaces are removed")
    void testPatch_Padded_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.padded();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));

        // WHEN
        service.patch(profile);

        // THEN
        verify(profileRepository).save(Profiles.valid());
    }

    @Test
    @DisplayName("When updating a profile, then the change is persisted")
    void testPatch_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.nameChange();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));

        // WHEN
        service.patch(profile);

        // THEN
        verify(profileRepository).save(Profiles.nameChange());
    }

    @Test
    @DisplayName("When updating a profile, then the change is returned")
    void testPatch_ReturnedData() {
        final Profile profile;
        final Profile updated;

        // GIVEN
        profile = Profiles.nameChange();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));
        given(profileRepository.save(Profiles.nameChange())).willReturn(Profiles.nameChange());

        // WHEN
        updated = service.patch(profile);

        // THEN
        Assertions.assertThat(updated)
            .as("profile")
            .isEqualTo(Profiles.nameChange());
    }

    @Test
    @DisplayName("When there is a profile with a not existing profile method, then an exception is thrown")
    void testPatch_WithContactChannel_NotExistingContactMethod() {
        final Profile          profile;
        final ThrowingCallable action;

        // GIVEN
        profile = Profiles.withEmail();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.patch(profile);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a profile with a not existing profile method, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("When patching a profile with a profile method, then the change is persisted")
    void testPatch_WithContactChannel_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.withEmail();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(profile);

        // THEN
        verify(profileRepository).save(Profiles.withEmail());
    }

}
