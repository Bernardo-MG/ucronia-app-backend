
package com.bernardomg.association.funds.test.balance.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.funds.balance.model.MonthlyBalance;

public final class BalanceAssertions {

    public static final void isEqualTo(final MonthlyBalance received, final MonthlyBalance expected) {
        Assertions.assertThat(received.getMonth())
            .withFailMessage("Expected date '%s' but got '%s'", expected.getMonth(), received.getMonth())
            .isEqualTo(expected.getMonth());
        Assertions.assertThat(received.getTotal())
            .withFailMessage("Expected total '%s' but got '%s'", expected.getTotal(), received.getTotal())
            .isEqualTo(expected.getTotal());
        Assertions.assertThat(received.getResults())
            .withFailMessage("Expected difference '%s' but got '%s'", expected.getResults(),
                received.getResults())
            .isEqualTo(expected.getResults());
    }

}
