
package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.test.config.factory.PersonConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeMaintenanceService")
public class TestFeeMaintenanceService {

    @Mock
    private FeeRepository                feeRepository;

    @Mock
    private MemberRepository             memberRepository;

    @InjectMocks
    private DefaultFeeMaintenanceService service;

    @Test
    @DisplayName("When there is a fee on the previous month and none in the current a new one is saved")
    void testRegisterMonthFees() {

        // GIVEN
        given(feeRepository.findAllForPreviousMonth()).willReturn(List.of(Fees.paidPreviousMonth()));
        given(memberRepository.isActive(PersonConstants.NUMBER)).willReturn(true);
        given(feeRepository.exists(PersonConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(false);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of(Fees.toCreate()));
    }

    @Test
    @DisplayName("When the fee exists nothing is saved")
    void testRegisterMonthFees_Exists() {

        // GIVEN
        given(feeRepository.findAllForPreviousMonth()).willReturn(List.of(Fees.paidPreviousMonth()));
        given(memberRepository.isActive(PersonConstants.NUMBER)).willReturn(true);
        given(feeRepository.exists(PersonConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(true);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of());
    }

    @Test
    @DisplayName("When the user is not active nothing is saved")
    void testRegisterMonthFees_NotActive() {

        // GIVEN
        given(feeRepository.findAllForPreviousMonth()).willReturn(List.of(Fees.paidPreviousMonth()));
        given(memberRepository.isActive(PersonConstants.NUMBER)).willReturn(false);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of());
    }

}
