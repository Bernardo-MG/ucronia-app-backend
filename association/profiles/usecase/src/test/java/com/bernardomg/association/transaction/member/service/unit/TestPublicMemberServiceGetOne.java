
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

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.association.member.usecase.service.DefaultPublicMemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultPublicMemberService - getOne")
class TestPublicMemberServiceGetOne {

    @Mock
    private PublicMemberRepository     memberRepository;

    @InjectMocks
    private DefaultPublicMemberService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<PublicMember> member;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(PublicMembers.active()));

        // WHEN
        member = service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is data, then it is returned")
            .contains(PublicMembers.active());
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
