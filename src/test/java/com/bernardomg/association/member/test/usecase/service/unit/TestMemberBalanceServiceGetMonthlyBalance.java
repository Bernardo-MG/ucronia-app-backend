
package com.bernardomg.association.member.test.usecase.service.unit;

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
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member balance service - get monthly balance")
class TestMemberBalanceServiceGetMonthlyBalance {

    @Mock
    private MemberBalanceRepository     memberBalanceRepository;

    @InjectMocks
    private DefaultMemberBalanceService service;

    @Test
    @DisplayName("Returns the queried data")
    void testGetMonthlyBalance() {
        final MemberBalanceQuery             query;
        final Sorting                        sorting;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sorting = Sorting.unsorted();

        given(memberBalanceRepository.findInRange(MemberBalanceConstants.PREVIOUS_MONTH,
            MemberBalanceConstants.CURRENT_MONTH, sorting)).willReturn(List.of(MonthlyMemberBalances.currentMonth()));

        query = MemberBalanceQueryRequests.previousAndThis();

        // WHEN
        balances = service.getMonthlyBalance(query, sorting);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyMemberBalances.currentMonth());
    }

    @Test
    @DisplayName("Can't read beyond the current month")
    void testGetMonthlyBalance_LimitsAtCurrent() {
        final MemberBalanceQuery query;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        query = MemberBalanceQueryRequests.aroundCurrent();

        // WHEN
        service.getMonthlyBalance(query, sorting);

        // THEN
        Mockito.verify(memberBalanceRepository)
            .findInRange(MemberBalanceConstants.PREVIOUS_MONTH, MemberBalanceConstants.CURRENT_MONTH, sorting);
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetMonthlyBalance_NoData() {
        final MemberBalanceQuery             query;
        final Sorting                        sorting;
        final Iterable<MonthlyMemberBalance> balances;

        // GIVEN
        sorting = Sorting.unsorted();

        given(memberBalanceRepository.findInRange(MemberBalanceConstants.PREVIOUS_MONTH,
            MemberBalanceConstants.CURRENT_MONTH, sorting)).willReturn(List.of());

        query = MemberBalanceQueryRequests.previousAndThis();

        // WHEN
        balances = service.getMonthlyBalance(query, sorting);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

}
