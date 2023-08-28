
package com.bernardomg.association.test.fee.service.integration;

import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.fee.service.DefaultFeeMaintenanceService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DefaultFeeMaintenanceService")
public class ITFeeMaintenanceService {

    @Autowired
    private FeeRepository                feeRepository;

    @Autowired
    private DefaultFeeMaintenanceService service;

    private final void registerFeeCurrentMonth() {
        final PersistentFee fee;
        final YearMonth     date;

        fee = new PersistentFee();
        fee.setMemberId(1l);
        fee.setPaid(false);

        date = YearMonth.now();
        fee.setDate(date);

        feeRepository.save(fee);
    }

    private final void registerFeePreviousMonth(final Boolean paid) {
        final PersistentFee fee;
        final YearMonth     date;

        fee = new PersistentFee();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        date = YearMonth.now()
            .minusMonths(1);
        fee.setDate(date);

        feeRepository.save(fee);
    }

    private final void registerFeeTwoMonthsBack(final Boolean paid) {
        final PersistentFee fee;
        final YearMonth     date;

        fee = new PersistentFee();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        date = YearMonth.now()
            .minusMonths(2);
        fee.setDate(date);

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
    @DisplayName("With a paid fee in the previous month, a new fee is registered")
    @Sql({ "/db/queries/member/single.sql" })
    void testRegisterMonthFees_PaidPreviousMonth_ExistingCurrentMonth() {
        final Long count;

        registerFeePreviousMonth(true);
        registerFeeCurrentMonth();

        service.registerMonthFees();

        count = feeRepository.count();
        Assertions.assertThat(count)
            .isEqualTo(2);
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

}
