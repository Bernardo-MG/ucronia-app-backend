
package com.bernardomg.association.funds.test.balance.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.funds.balance.model.MonthlyBalance;

public final class BalanceAssertions {

    public static final void isEqualTo(final MonthlyBalance received, final MonthlyBalance expected) {
        Assertions.assertThat(received.getDate())
            .withFailMessage("Expected date '%s' but got '%s'", expected.getDate(), received.getDate())
            .isEqualTo(expected.getDate());
        Assertions.assertThat(received.getTotal())
            .withFailMessage("Expected total '%s' but got '%s'", expected.getTotal(), received.getTotal())
            .isEqualTo(expected.getTotal());
        Assertions.assertThat(received.getCumulative())
            .withFailMessage("Expected cumulative '%s' but got '%s'", expected.getCumulative(),
                received.getCumulative())
            .isEqualTo(expected.getCumulative());
    }

}
