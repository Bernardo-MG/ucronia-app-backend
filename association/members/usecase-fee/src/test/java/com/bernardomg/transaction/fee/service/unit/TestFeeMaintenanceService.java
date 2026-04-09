
package com.bernardomg.transaction.fee.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
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
import com.bernardomg.association.fee.domain.repository.FeeMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeMembers;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeMaintenanceService")
public class TestFeeMaintenanceService {

    @Mock
    private FeeMemberRepository          feeMemberRepository;

    @Mock
    private FeeRepository                feeRepository;

    @InjectMocks
    private DefaultFeeMaintenanceService service;

    @Test
    @DisplayName("When there is a member to renew a new unpaid fee is saved")
    void testRegisterMonthFees() {

        // GIVEN
        given(feeMemberRepository.findAllToRenew()).willReturn(List.of(FeeMembers.valid()));
        given(feeMemberRepository.findFeeType(ProfileConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.exists(ProfileConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(false);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).saveAll(List.of(Fees.notPaidCurrentMonth()));
    }

    @Test
    @DisplayName("When there is a member to renew, but the fee already exists, nothing is saved")
    void testRegisterMonthFees_Exists() {

        // GIVEN
        given(feeMemberRepository.findAllToRenew()).willReturn(List.of(FeeMembers.valid()));
        given(feeMemberRepository.findFeeType(ProfileConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.exists(ProfileConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(true);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).saveAll(List.of());
    }

    @Test
    @DisplayName("When the fee type doesn't exist, an exception is thrown")
    void testRegisterMonthFees_NoFeeType() {
        final ThrowingCallable execution;

        // GIVEN
        given(feeMemberRepository.findAllToRenew()).willReturn(List.of(FeeMembers.valid()));
        given(feeMemberRepository.findFeeType(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.registerMonthFees();

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeTypeException.class);
    }

    @Test
    @DisplayName("When there are no members to renew, nothing is saved")
    void testRegisterMonthFees_NotActive() {

        // GIVEN
        given(feeMemberRepository.findAllToRenew()).willReturn(List.of());

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).saveAll(List.of());
    }

    @Test
    @DisplayName("When there is a member to renew, and the fee type has zero amount, a new paid fee is saved")
    void testRegisterMonthFees_ZeroAmount() {

        // GIVEN
        given(feeMemberRepository.findAllToRenew()).willReturn(List.of(FeeMembers.valid()));
        given(feeMemberRepository.findFeeType(ProfileConstants.NUMBER)).willReturn(Optional.of(FeeTypes.zero()));
        given(feeRepository.exists(ProfileConstants.NUMBER, FeeConstants.CURRENT_MONTH)).willReturn(false);

        // WHEN
        service.registerMonthFees();

        // THEN
        verify(feeRepository).saveAll(List.of(Fees.paidNoTransactionCurrentMonthNoAmount()));
    }

}
