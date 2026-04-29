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

package com.bernardomg.association.security.user.test.service.unit;

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

import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile;
import com.bernardomg.association.security.account.domain.repository.AccountProfileRepository;
import com.bernardomg.association.security.account.test.configuration.factory.AccountProfileConstants;
import com.bernardomg.association.security.account.test.configuration.factory.AccountProfiles;
import com.bernardomg.association.security.user.domain.exception.MissingAccountProfileException;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.Users;
import com.bernardomg.association.security.user.usecase.service.DefaultUserProfileService;
import com.bernardomg.security.user.domain.exception.MissingUsernameException;
import com.bernardomg.security.user.domain.repository.UserRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserProfileService - assign profile")
class TestUserProfileServiceAssignProfile {

    @Mock
    private AccountProfileRepository  accountProfileRepository;

    @InjectMocks
    private DefaultUserProfileService service;

    @Mock
    private UserProfileRepository     userProfileRepository;

    @Mock
    private UserRepository            userRepository;

    @Test
    @DisplayName("When the profile has already been assigned, it throws an exception")
    void testAssignProfile_ExistingProfile() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(accountProfileRepository.findOne(AccountProfileConstants.NUMBER))
            .willReturn(Optional.of(AccountProfiles.valid()));

        given(
            userProfileRepository.existsByProfileForAnotherUser(UserConstants.USERNAME, AccountProfileConstants.NUMBER))
                .willReturn(true);

        // WHEN
        execution = () -> service.assignProfile(UserConstants.USERNAME, AccountProfileConstants.NUMBER);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "profile", AccountProfileConstants.NUMBER));
    }

    @Test
    @DisplayName("With no profile, it throws an exception")
    void testAssignProfile_NoProfile() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(accountProfileRepository.findOne(AccountProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.assignProfile(UserConstants.USERNAME, AccountProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAccountProfileException.class);
    }

    @Test
    @DisplayName("With no user, it throws an exception")
    void testAssignProfile_NoUser() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.assignProfile(UserConstants.USERNAME, AccountProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingUsernameException.class);
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    void testAssignProfile_Persisted() {

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(accountProfileRepository.findOne(AccountProfileConstants.NUMBER))
            .willReturn(Optional.of(AccountProfiles.valid()));

        // WHEN
        service.assignProfile(UserConstants.USERNAME, AccountProfileConstants.NUMBER);

        // THEN
        verify(userProfileRepository).assignProfile(UserConstants.USERNAME, AccountProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    void testAssignProfile_Returned() {
        final Profile profile;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(accountProfileRepository.findOne(AccountProfileConstants.NUMBER))
            .willReturn(Optional.of(AccountProfiles.valid()));
        given(userProfileRepository.assignProfile(UserConstants.USERNAME, AccountProfileConstants.NUMBER))
            .willReturn(AccountProfiles.valid());

        // WHEN
        profile = service.assignProfile(UserConstants.USERNAME, AccountProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .isEqualTo(AccountProfiles.valid());
    }

}
