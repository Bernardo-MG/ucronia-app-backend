
package com.bernardomg.association.transaction.member.service.unit;

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

import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.exception.MemberExistsException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultProfileMembershipService;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultProfileMembershipService - convertToMember")
class TestProfileMembershipServiceConvert {

    @Mock
    private FeeTypeRepository               feeTypeRepository;

    @Mock
    private MemberRepository                memberRepository;

    @Mock
    private ProfileRepository               profileRepository;

    @InjectMocks
    private DefaultProfileMembershipService service;

    @Test
    @DisplayName("When there is an existing member, then an exception is thrown")
    void testConvertToMember_ExistingMember_Exception() {
        final ThrowingCallable action;
        final Profile          member;

        // GIVEN
        member = Profiles.withType(Member.PROFILE_TYPE);
        given(profileRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(member));

        // WHEN
        action = () -> service.convertToMember(MemberConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is an existing member, then an exception is thrown")
            .isInstanceOf(MemberExistsException.class);
    }

    @Test
    @DisplayName("When there is a not existing fee type, then an exception is thrown")
    void testConvertToMember_NotExistingFeeType_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(profileRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));
        given(feeTypeRepository.exists(MemberConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.convertToMember(MemberConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing fee type, then an exception is thrown")
            .isInstanceOf(MissingFeeTypeException.class);
    }

    @Test
    @DisplayName("When there is a not existing member, then an exception is thrown")
    void testConvertToMember_NotExistingProfile_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(profileRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.convertToMember(MemberConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing member, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When converting to member, then the change is persisted")
    void testConvertToMember_PersistedData() {

        // GIVEN
        given(profileRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));
        given(feeTypeRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberRepository.save(Members.toCreate())).willReturn(Members.withoutType());

        // WHEN
        service.convertToMember(MemberConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        verify(memberRepository).save(Members.toCreate());
    }

    @Test
    @DisplayName("When converting to member, then the change is returned")
    void testConvertToMember_ReturnedData() {
        final Member updated;

        // GIVEN
        given(profileRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));
        given(feeTypeRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberRepository.save(Members.toCreate())).willReturn(Members.withoutType());

        // WHEN
        updated = service.convertToMember(MemberConstants.NUMBER, FeeConstants.FEE_TYPE_NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(Members.withoutType());
    }

}
