
package com.bernardomg.association.membership.test.fee.service.integration;

import java.time.YearMonth;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.fee.service.DefaultFeeMaintenanceService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DefaultFeeMaintenanceService")
public class ITFeeMaintenanceService {

    private static final YearMonth       CURRENT_MONTH  = YearMonth.now();

    private static final YearMonth       PREVIOUS_MONTH = YearMonth.now()
        .minusMonths(1);

    private static final YearMonth       TWO_BACK_MONTH = YearMonth.now()
        .minusMonths(2);

    @Autowired
    private FeeRepository                feeRepository;

    @Autowired
    private DefaultFeeMaintenanceService service;

    private final void registerFeeCurrentMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(CURRENT_MONTH);

        feeRepository.save(fee);
    }

    private final void registerFeePreviousMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(PREVIOUS_MONTH);

        feeRepository.save(fee);
    }

    private final void registerFeeTwoMonthsBack(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(TWO_BACK_MONTH);

        feeRepository.save(fee);
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
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_NoFees() {
        final Long count;

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

    @Test
    @DisplayName("With a paid fee in the previous month, a new fee is registered")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidPreviousMonth() {
        final Long count;

        registerFeePreviousMonth(true);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and a paid one this one, no new fee is registered")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidPreviousMonth_PaidCurrentMonth() {
        final Long count;

        registerFeePreviousMonth(true);
        registerFeeCurrentMonth(true);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and a paid one this one, all fees are paid")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidPreviousMonth_PaidCurrentMonth_Status() {
        final Collection<FeeEntity> fees;

        registerFeePreviousMonth(true);
        registerFeeCurrentMonth(true);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .allMatch(fee -> fee.getPaid());
    }

    @Test
    @DisplayName("With a paid fee in the previous month, a new fee is registered, the paid status is set correctly")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidPreviousMonth_Status() {
        final Collection<FeeEntity> fees;

        registerFeePreviousMonth(true);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(PREVIOUS_MONTH))
            .allMatch(fee -> fee.getPaid());
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(CURRENT_MONTH))
            .allMatch(fee -> !fee.getPaid());
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and an unpaid one this one, no new fee is registered")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidPreviousMonth_UnpaidCurrentMonth() {
        final Long count;

        registerFeePreviousMonth(true);
        registerFeeCurrentMonth(false);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and an unpaid one this one, all fees are paid")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidPreviousMonth_UnpaidCurrentMonth_NoStatusChange() {
        final Collection<FeeEntity> fees;

        registerFeePreviousMonth(true);
        registerFeeCurrentMonth(false);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(PREVIOUS_MONTH))
            .allMatch(fee -> fee.getPaid());
        Assertions.assertThat(fees)
            .filteredOn(fee -> fee.getDate()
                .equals(CURRENT_MONTH))
            .allMatch(fee -> !fee.getPaid());
    }

    @Test
    @DisplayName("With a paid fee two months back, no fee is registered")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidTwoMonthsBack() {
        final Long count;

        registerFeeTwoMonthsBack(true);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a paid fee two months back, the paid status doesn't change")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidTwoMonthsBack_Status() {
        final Collection<FeeEntity> fees;

        registerFeeTwoMonthsBack(true);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .allMatch(fee -> fee.getPaid());
    }

    @Test
    @DisplayName("With an unpaid fee in the previous month, a new fee is registered")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_UnpaidPreviousMonth() {
        final Long count;

        registerFeePreviousMonth(false);

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With an unpaid fee in the previous month, all fees are unpaid")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_UnpaidPreviousMonth_Status() {
        final Collection<FeeEntity> fees;

        registerFeePreviousMonth(false);

        service.registerMonthFees();

        fees = feeRepository.findAll();
        Assertions.assertThat(fees)
            .allMatch(fee -> !fee.getPaid());
    }

}
