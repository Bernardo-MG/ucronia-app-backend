
package com.bernardomg.association.membership.test.fee.service.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DefaultFeeMaintenanceService")
public class ITFeeMaintenanceService {

    @Autowired
    private FeeInitializer               feeInitializer;

    @Autowired
    private FeeRepository                feeRepository;

    @Autowired
    private DefaultFeeMaintenanceService service;

    @Test
    @DisplayName("With a paid fee in the previous month, and a paid one this one, no new fee is registered")
    @ValidMember
    void testRegisterMonthFees_CurrentMonth_PreviousMonth_Paid() {
        final Long count;

        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(true);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and a paid one this one, all fees are paid")
    @ValidMember
    void testRegisterMonthFees_CurrentMonth_PreviousMonth_Paid_Status() {
        final Collection<FeeEntity> fees;

        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(true);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .allMatch(fee -> fee.getPaid());
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and an unpaid one this one, no new fee is registered")
    @ValidMember
    void testRegisterMonthFees_CurrentMonth_Unpaid_PreviousMonth_Paid() {
        final Long count;

        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(false);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and an unpaid one this one, all fees are paid")
    @ValidMember
    void testRegisterMonthFees_CurrentMonth_Unpaid_PreviousMonth_Paid_NoStatusChange() {
        final Collection<FeeEntity> fees;

        feeInitializer.registerFeePreviousMonth(true);
        feeInitializer.registerFeeCurrentMonth(false);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(FeeInitializer.PREVIOUS_MONTH))
            .allMatch(fee -> fee.getPaid());
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(FeeInitializer.CURRENT_MONTH))
            .allMatch(fee -> !fee.getPaid());
    }

    @Test
    @DisplayName("With a paid fee next month, no fee is registered")
    @ValidMember
    void testRegisterMonthFees_NextMonth_Paid() {
        final Long count;

        feeInitializer.registerFeeNextMonth(true);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a paid fee next month, the paid status doesn't change")
    @ValidMember
    void testRegisterMonthFees_NextMonth_Paid_Status() {
        final Collection<FeeEntity> fees;

        feeInitializer.registerFeeNextMonth(true);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .allMatch(fee -> fee.getPaid());
    }

    @Test
    @DisplayName("With no data, nothing is registered")
    void testRegisterMonthFees_NoData() {
        final Long count;

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("With no fees, nothing is registered")
    @ValidMember
    void testRegisterMonthFees_NoFees() {
        final Long count;

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("With a paid fee in the previous month, a new fee is registered")
    @ValidMember
    void testRegisterMonthFees_PreviousMonth_Paid() {
        final Long count;

        feeInitializer.registerFeePreviousMonth(true);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a paid fee in the previous month, a new fee is registered, the paid status is set correctly")
    @ValidMember
    void testRegisterMonthFees_PreviousMonth_Paid_Status() {
        final Collection<FeeEntity> fees;

        feeInitializer.registerFeePreviousMonth(true);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(FeeInitializer.PREVIOUS_MONTH))
            .allMatch(fee -> fee.getPaid());
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(FeeInitializer.CURRENT_MONTH))
            .allMatch(fee -> !fee.getPaid());
    }

    @Test
    @DisplayName("With an unpaid fee in the previous month, a new fee is registered")
    @ValidMember
    void testRegisterMonthFees_PreviousMonth_Unpaid() {
        final Long count;

        feeInitializer.registerFeePreviousMonth(false);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With an unpaid fee in the previous month, all fees are unpaid")
    @ValidMember
    void testRegisterMonthFees_PreviousMonth_Unpaid_Status() {
        final Collection<FeeEntity> fees;

        feeInitializer.registerFeePreviousMonth(false);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .allMatch(fee -> !fee.getPaid());
    }

    @Test
    @DisplayName("With a paid fee two months back, no fee is registered")
    @ValidMember
    void testRegisterMonthFees_TwoMonthsBack_Paid() {
        final Long count;

        feeInitializer.registerFeeTwoMonthsBack(true);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a paid fee two months back, the paid status doesn't change")
    @ValidMember
    void testRegisterMonthFees_TwoMonthsBack_Paid_Status() {
        final Collection<FeeEntity> fees;

        feeInitializer.registerFeeTwoMonthsBack(true);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .allMatch(fee -> fee.getPaid());
    }
}
