
package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberService - getOne")
class TestMemberServiceGetOne {

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
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<Member> member;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        member = service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is data, then it is returned")
            .contains(Members.active());
    }

    @Test
    @DisplayName("When the member doesn't exist, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the member doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingMemberException.class);
    }

}
