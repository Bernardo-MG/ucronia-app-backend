
package com.bernardomg.association.transaction.member.service.unit;

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

import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
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
@DisplayName("DefaultMemberService - create")
class TestMemberServiceCreate {

    @Mock
    private FeeTypeRepository             feeTypeRepository;

    @Mock
    private MemberContactMethodRepository memberContactMethodRepository;

    @Mock
    private MemberRepository              memberRepository;

    @InjectMocks
    private DefaultMemberService          service;

    @Test
    @DisplayName("When there is a missing contact method, then an exception is thrown")
    void testCreate_ContactMethodMissing() {
        final Member           member;
        final ThrowingCallable action;

        // GIVEN
        member = Members.withEmail();
        given(feeTypeRepository.exists(member.feeType()
            .number())).willReturn(true);

        given(memberContactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(member);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing contact method, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("When there is a missing fee type, then an exception is thrown")
    void testCreate_FeeTypeMissing() {
        final Member           member;
        final ThrowingCallable action;

        // GIVEN
        member = Members.active();
        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(member);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing fee type, then an exception is thrown")
            .isInstanceOf(MissingFeeTypeException.class);
    }

    @Test
    @DisplayName("When there is a member with an existing identifier, then an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable action;
        final Member           member;

        // GIVEN
        member = Members.active();

        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);
        given(memberRepository.existsByIdentifier(MemberConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        action = () -> service.create(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", MemberConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a member having padding whitespaces in first and last name, then these whitespaces are removed and the profile is persisted")
    void testCreate_Padded_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.padded();

        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);

        // WHEN
        service.create(member);

        // THEN
        verify(memberRepository).save(Members.active());
    }

    @Test
    @DisplayName("When there is a valid member, then the profile is persisted")
    void testCreate_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.active();

        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);

        // WHEN
        service.create(member);

        // THEN
        verify(memberRepository).save(Members.active());
    }

    @Test
    @DisplayName("When there is a valid member, then the created profile is returned")
    void testCreate_ReturnedData() {
        final Member member;
        final Member created;

        // GIVEN
        member = Members.active();

        given(memberRepository.save(member)).willReturn(Members.active());
        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);

        // WHEN
        created = service.create(member);

        // THEN
        Assertions.assertThat(created)
            .as("profile")
            .isEqualTo(Members.active());
    }

}
