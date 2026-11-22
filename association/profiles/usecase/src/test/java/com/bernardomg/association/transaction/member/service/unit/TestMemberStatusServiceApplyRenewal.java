
package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberStatusService - applyRenewal")
class TestMemberStatusServiceApplyRenewal {

    @Mock
    private MemberRepository           memberRepository;

    @InjectMocks
    private DefaultMemberStatusService service;

    @Test
    @DisplayName("When applying renewal to an active membership, then the member is deactivated")
    void testApplyRenewal_Active() {
        // GIVEN
        given(memberRepository.findAllWithRenewalMismatch()).willReturn(List.of(Members.active()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(memberRepository).saveAll(List.of(Members.inactiveNoRenew()));
    }

    @Test
    @DisplayName("When applying renewal to an inactive membership and an active membership, then both are updated")
    void testApplyRenewal_ActiveAndInactive() {
        // GIVEN
        given(memberRepository.findAllWithRenewalMismatch())
            .willReturn(List.of(Members.active(), Members.alternativeInactive()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(memberRepository).saveAll(List.of(Members.alternativeActive(), Members.inactiveNoRenew()));
    }

    @Test
    @DisplayName("When applying renewal to an inactive membership, then the member is activated")
    void testApplyRenewal_Inactive() {
        // GIVEN
        given(memberRepository.findAllWithRenewalMismatch()).willReturn(List.of(Members.inactive()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(memberRepository).saveAll(List.of(Members.active()));
    }

}
