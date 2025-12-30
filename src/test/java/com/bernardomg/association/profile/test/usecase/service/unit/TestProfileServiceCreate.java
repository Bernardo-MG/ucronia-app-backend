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
import org.mockito.Mockito;
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
@DisplayName("Profile service - create")
class TestProfileServiceCreate {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ProfileRepository       profileRepository;

    @InjectMocks
    private DefaultProfileService   service;

    public TestProfileServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a profile with an existing identifier, an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable execution;
        final Profile          profile;

        // GIVEN
        profile = Profiles.toCreate();

        given(profileRepository.existsByIdentifier(ProfileConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        execution = () -> service.create(profile);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a profile with an existing identifier, but the identifier is empty, no exception is thrown")
    void testCreate_IdentifierExistsAndEmpty() {
        final Profile profile;

        // GIVEN
        profile = Profiles.toCreateNoIdentifier();

        // WHEN
        service.create(profile);

        // THEN
        verify(profileRepository).save(Profiles.toCreateNoIdentifier());
        verify(profileRepository, Mockito.never()).existsByIdentifier(ProfileConstants.IDENTIFIER);
    }

    @Test
    @DisplayName("With a profile having padding whitespaces in the texts, these whitespaces are removed and the profile is persisted")
    void testCreate_Padded_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.padded();

        // WHEN
        service.create(profile);

        // THEN
        verify(profileRepository).save(Profiles.toCreate());
    }

    @Test
    @DisplayName("With a valid profile, the profile is persisted")
    void testCreate_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.toCreate();

        // WHEN
        service.create(profile);

        // THEN
        verify(profileRepository).save(Profiles.toCreate());
    }

    @Test
    @DisplayName("With a valid profile, the created profile is returned")
    void testCreate_ReturnedData() {
        final Profile profile;
        final Profile created;

        // GIVEN
        profile = Profiles.toCreate();

        given(profileRepository.save(Profiles.toCreate())).willReturn(Profiles.created());

        // WHEN
        created = service.create(profile);

        // THEN
        Assertions.assertThat(created)
            .as("profile")
            .isEqualTo(Profiles.created());
    }

    @Test
    @DisplayName("With a profile with a not existing profile method, an exception is thrown")
    void testCreate_WithContactChannel_NotExisting() {
        final Profile          profile;
        final ThrowingCallable execution;

        // GIVEN
        profile = Profiles.withEmail();

        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(profile);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("With a profile with a profile method, the profile is persisted")
    void testCreate_WithContactChannel_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.withEmail();

        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(profile);

        // THEN
        verify(profileRepository).save(Profiles.toCreateWithEmail());
    }

}
