
package com.bernardomg.association.member.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.model.MemberBalanceQuery;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.domain.repository.MemberBalanceRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberBalanceConstants;
import com.bernardomg.association.member.test.configuration.factory.MemberBalanceQueryRequests;
import com.bernardomg.association.member.test.configuration.factory.MonthlyMemberBalances;
import com.bernardomg.association.member.usecase.service.DefaultMemberBalanceService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member balance service - get monthly balance")
class TestMemberBalanceServiceGetMonthlyBalance {

    @Mock
    private MemberBalanceRepository     memberBalanceRepository;

    @InjectMocks
    private DefaultMemberBalanceService service;

    @Test
    @DisplayName("Returns the queried data when covering previous and current")
    void testGetMonthlyBalance_CoversBoth() {
        final MemberBalanceQuery             query;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        given(memberBalanceRepository.findInRange(eq(MemberBalanceConstants.PREVIOUS_MONTH),
            eq(MemberBalanceConstants.CURRENT_MONTH), any())).willReturn(List.of(MonthlyMemberBalances.currentMonth()));

        query = MemberBalanceQueryRequests.previousAndThis();

        // WHEN
        balances = service.getMonthlyBalance(query);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("Can't read beyond the current month")
    void testGetMonthlyBalance_LimitsAtCurrent() {
        final MemberBalanceQuery query;

        // GIVEN
        query = MemberBalanceQueryRequests.aroundCurrent();

        // WHEN
        service.getMonthlyBalance(query);

        // THEN
        Mockito.verify(memberBalanceRepository)
            .findInRange(eq(MemberBalanceConstants.PREVIOUS_MONTH), eq(MemberBalanceConstants.CURRENT_MONTH), any());
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetMonthlyBalance_NoData() {
        final MemberBalanceQuery             query;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        given(memberBalanceRepository.findInRange(eq(MemberBalanceConstants.PREVIOUS_MONTH),
            eq(MemberBalanceConstants.CURRENT_MONTH), any())).willReturn(List.of());

        query = MemberBalanceQueryRequests.previousAndThis();

        // WHEN
        balances = service.getMonthlyBalance(query);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

}
