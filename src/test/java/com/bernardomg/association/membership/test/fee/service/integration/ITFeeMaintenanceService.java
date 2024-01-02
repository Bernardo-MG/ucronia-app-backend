
package com.bernardomg.association.membership.test.fee.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.service.DefaultFeeMaintenanceService;
import com.bernardomg.association.membership.test.fee.config.argument.FeeMonthPaidArgumentsProvider;
import com.bernardomg.association.membership.test.fee.config.argument.FeePaidArgumentsProvider;
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

    @DisplayName("With a fee in the previous month and another in the current, the fees don't change")
    @ParameterizedTest(name = "Previous paid: {0}, current paid: {1}")
    @ArgumentsSource(FeeMonthPaidArgumentsProvider.class)
    @ValidMember
    void testRegisterMonthFees_CurrentPreviousPayment_Params(final boolean previous, final boolean current) {

        // GIVEN
        feeInitializer.registerFeePreviousMonth(previous);
        feeInitializer.registerFeeCurrentMonth(current);

        // WHEN
        service.registerMonthFees();

        // THEN
        Assertions.assertThat(feeRepository.count())
            .as("fees count")
            .isEqualTo(2);
    }

    @DisplayName("With a fee on the next month, no fee is registered")
    @ParameterizedTest(name = "Paid: {0}")
    @ArgumentsSource(FeePaidArgumentsProvider.class)
    @ValidMember
    void testRegisterMonthFees_NextMonth(final boolean paid) {

        // GIVEN
        feeInitializer.registerFeeNextMonth(paid);

        // WHEN
        service.registerMonthFees();

        // THEN
        Assertions.assertThat(feeRepository.count())
            .as("fees count")
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With no data, nothing is registered")
    void testRegisterMonthFees_NoData() {
        final Long count;

        // WHEN
        service.registerMonthFees();

        // THEN
        count = feeRepository.count();
        Assertions.assertThat(count)
            .as("fees count")
            .isZero();
    }

    @Test
    @DisplayName("With no fees, nothing is registered")
    @ValidMember
    void testRegisterMonthFees_NoFees() {
        final Long count;

        // WHEN
        service.registerMonthFees();

        // THEN
        count = feeRepository.count();
        Assertions.assertThat(count)
            .as("fees count")
            .isZero();
    }

    @DisplayName("With an fee in the previous month, a new fee is registered")
    @ParameterizedTest(name = "Paid: {0}")
    @ArgumentsSource(FeePaidArgumentsProvider.class)
    @ValidMember
    void testRegisterMonthFees_PreviousMonth(final boolean paid) {

        // GIVEN
        feeInitializer.registerFeePreviousMonth(paid);

        // WHEN
        service.registerMonthFees();

        // THEN
        Assertions.assertThat(feeRepository.count())
            .as("fees count")
            .isEqualTo(2);
    }

    @DisplayName("With a paid fee two months back, no fee is registered")
    @ParameterizedTest(name = "Paid: {0}")
    @ArgumentsSource(FeePaidArgumentsProvider.class)
    @ValidMember
    void testRegisterMonthFees_TwoMonthsBack(final boolean paid) {

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(paid);

        // WHEN
        service.registerMonthFees();

        // THEN
        Assertions.assertThat(feeRepository.count())
            .as("fees count")
            .isEqualTo(1);
    }
}
