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

package com.bernardomg.association.member.test.usecase.service.unit;

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

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.exception.MemberExistsException;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.member.usecase.service.DefaultProfileMembershipService;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfileMembershipService - convert to member")
class TestProfileMembershipServiceConvert {

    @Mock
    private MemberProfileRepository         memberProfileRepository;

    @Mock
    private ProfileRepository               profileRepository;

    @InjectMocks
    private DefaultProfileMembershipService service;

    @Test
    @DisplayName("With an existing member, an exception is thrown")
    void testConvertToMember_ExistingMember_Exception() {
        final ThrowingCallable execution;
        final Profile          profile;

        // GIVEN
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(memberProfileRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.convertToMember(ProfileConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MemberExistsException.class);
    }

    @Test
    @DisplayName("With a not existing profile, an exception is thrown")
    void testConvertToMember_NotExistingContact_Exception() {
        final ThrowingCallable execution;

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.convertToMember(ProfileConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When converting to member, the change is persisted")
    void testConvertToMember_PersistedData() {
        final MemberProfile member;
        final Profile       profile;

        // GIVEN
        member = MemberProfiles.withoutType();
        profile = Profiles.withEmail();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(memberProfileRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        service.convertToMember(ProfileConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        verify(memberProfileRepository).save(member, ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When converting to member, the change is returned")
    void testConvertToMember_ReturnedData() {
        final MemberProfile member;
        final Profile       profile;
        final MemberProfile updated;

        // GIVEN
        member = MemberProfiles.withoutType();
        profile = Profiles.withEmail();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(memberProfileRepository.exists(ProfileConstants.NUMBER)).willReturn(false);
        given(memberProfileRepository.save(member, ProfileConstants.NUMBER)).willReturn(member);

        // WHEN
        updated = service.convertToMember(ProfileConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(MemberProfiles.withoutType());
    }

}
