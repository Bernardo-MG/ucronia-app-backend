
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
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
@DisplayName("DefaultProfileService - create")
class TestProfileServiceCreate {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ProfileRepository       profileRepository;

    @InjectMocks
    private DefaultProfileService   service;

    @Test
    @DisplayName("When there is a profile with an existing identifier, then an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable action;
        final Profile          profile;

        // GIVEN
        profile = Profiles.valid();

        given(profileRepository.existsByIdentifier(ProfileConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        action = () -> service.create(profile);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a profile having padding whitespaces in the texts, then these whitespaces are removed and the profile is persisted")
    void testCreate_Padded_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.padded();

        // WHEN
        service.create(profile);

        // THEN
        verify(profileRepository).save(Profiles.valid());
    }

    @Test
    @DisplayName("When there is a valid profile, then the profile is persisted")
    void testCreate_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        service.create(profile);

        // THEN
        verify(profileRepository).save(Profiles.valid());
    }

    @Test
    @DisplayName("When there is a valid profile, then the created profile is returned")
    void testCreate_ReturnedData() {
        final Profile profile;
        final Profile created;

        // GIVEN
        profile = Profiles.valid();

        given(profileRepository.save(Profiles.valid())).willReturn(Profiles.created());

        // WHEN
        created = service.create(profile);

        // THEN
        Assertions.assertThat(created)
            .as("profile")
            .isEqualTo(Profiles.created());
    }

    @Test
    @DisplayName("When there is a profile with a not existing profile method, then an exception is thrown")
    void testCreate_WithContactChannel_NotExisting() {
        final Profile          profile;
        final ThrowingCallable action;

        // GIVEN
        profile = Profiles.withEmail();

        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(profile);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a profile with a not existing profile method, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("When there is a profile with a profile method, then the profile is persisted")
    void testCreate_WithContactChannel_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.withEmail();

        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(profile);

        // THEN
        verify(profileRepository).save(Profiles.withEmail());
    }

}
