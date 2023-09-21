
package com.bernardomg.association.funds.test.balance.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.funds.balance.model.MonthlyBalance;

public final class BalanceAssertions {

    public static final void isEqualTo(final MonthlyBalance received, final MonthlyBalance expected) {
        Assertions.assertThat(received.getMonth())
            .withFailMessage("Expected date '%s' but got '%s'", expected.getMonth(), received.getMonth())
            .isEqualTo(expected.getMonth());
        Assertions.assertThat(received.getMonthlyTotal())
            .withFailMessage("Expected total '%s' but got '%s'", expected.getMonthlyTotal(), received.getMonthlyTotal())
            .isEqualTo(expected.getMonthlyTotal());
        Assertions.assertThat(received.getCumulative())
            .withFailMessage("Expected cumulative '%s' but got '%s'", expected.getCumulative(),
                received.getCumulative())
            .isEqualTo(expected.getCumulative());
    }

}
