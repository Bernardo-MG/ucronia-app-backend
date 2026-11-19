
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
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberService - update")
class TestMemberServiceUpdate {

    @Mock
    private FeeTypeRepository             feeTypeRepository;

    @Mock
    private KeyRepository                 keyRepository;

    @Mock
    private MemberContactMethodRepository memberContactMethodRepository;

    @Mock
    private MemberRepository              memberRepository;

    @InjectMocks
    private DefaultMemberService          service;

    @Test
    @DisplayName("When there is a missing contact method, then an exception is thrown")
    void testUpdate_ContactMethodMissing() {
        final Member           member;
        final ThrowingCallable action;

        // GIVEN
        member = Members.withEmail();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(member.feeType()
            .number())).willReturn(true);
        given(memberContactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing contact method, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("When there is a missing fee type, then an exception is thrown")
    void testUpdate_FeeTypeMissing() {
        final Member           member;
        final ThrowingCallable action;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing fee type, then an exception is thrown")
            .isInstanceOf(MissingFeeTypeException.class);
    }

    @Test
    @DisplayName("When there is a profile with an existing identifier, then an exception is thrown")
    void testUpdate_IdentifierExists() {
        final ThrowingCallable action;
        final Member           member;

        // GIVEN
        member = Members.active();

        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.existsByIdentifierForAnother(MemberConstants.NUMBER, MemberConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        action = () -> service.update(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", MemberConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a not existing member, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Member           member;
        final ThrowingCallable action;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing member, then an exception is thrown")
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("When there is a member having padding whitespaces in first and last name, then these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.padded();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(member);

        // THEN
        verify(memberRepository).save(Members.active());
    }

    @Test
    @DisplayName("When updating a member, then the change is persisted")
    void testUpdate_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.nameChange();
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(memberRepository.save(Members.nameChange())).willReturn(Members.nameChange());

        // WHEN
        service.update(member);

        // THEN
        verify(memberRepository).save(Members.nameChange());
    }

    @Test
    @DisplayName("When updating a member, then the change is returned")
    void testUpdate_ReturnedData() {
        final Member member;
        final Member updated;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(memberRepository.save(Members.nameChange())).willReturn(Members.nameChange());

        // WHEN
        updated = service.update(member);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(Members.nameChange());
    }

}
