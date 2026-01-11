
package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeMaintenanceService")
public class TestFeeMaintenanceService {

    @Mock
    private FeeRepository                feeRepository;

    @Mock
    private FeeTypeRepository            feeTypeRepository;

    @Mock
    private MemberProfileRepository      memberProfileRepository;

    @InjectMocks
    private DefaultFeeMaintenanceService service;

    @Test
    @DisplayName("When there is a member to renew a new unpaid fee is saved")
    void testRegisterMonthFees() {

        // GIVEN
        given(memberProfileRepository.findAllToRenew()).willReturn(List.of(MemberProfiles.active()));
        given(feeRepository.exists(ProfileConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(false);
        given(feeTypeRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of(Fees.notPaidCurrentMonth()));
    }

    @Test
    @DisplayName("When there is a member to renew, but the fee already exists, nothing is saved")
    void testRegisterMonthFees_Exists() {

        // GIVEN
        given(memberProfileRepository.findAllToRenew()).willReturn(List.of(MemberProfiles.active()));
        given(feeRepository.exists(ProfileConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(true);
        given(feeTypeRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of());
    }

    @Test
    @DisplayName("When there are no members to renew, nothing is saved")
    void testRegisterMonthFees_NotActive() {

        // GIVEN
        given(memberProfileRepository.findAllToRenew()).willReturn(List.of());

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of());
    }

    @Test
    @DisplayName("When there is a member to renew, and the fee type has zero amount, a new paid fee is saved")
    void testRegisterMonthFees_ZeroAmount() {

        // GIVEN
        given(memberProfileRepository.findAllToRenew()).willReturn(List.of(MemberProfiles.active()));
        given(feeRepository.exists(ProfileConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(false);
        given(feeTypeRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(FeeTypes.zero()));

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).save(List.of(Fees.paidNoTransactionCurrentMonth()));
    }

}
