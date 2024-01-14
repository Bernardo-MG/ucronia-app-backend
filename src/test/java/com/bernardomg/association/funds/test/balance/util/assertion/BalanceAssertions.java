
package com.bernardomg.association.funds.test.balance.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.transaction.model.MonthlyBalance;

public final class BalanceAssertions {

    public static final void isEqualTo(final MonthlyBalance received, final MonthlyBalance expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getMonth())
                .as("month")
                .withFailMessage("Expected date '%s' but got '%s'", expected.getMonth(), received.getMonth())
                .isEqualTo(expected.getMonth());
            softly.assertThat(received.getTotal())
                .as("total")
                .withFailMessage("Expected total '%s' but got '%s'", expected.getTotal(), received.getTotal())
                .isEqualTo(expected.getTotal());
            softly.assertThat(received.getResults())
                .as("results")
                .withFailMessage("Expected difference '%s' but got '%s'", expected.getResults(), received.getResults())
                .isEqualTo(expected.getResults());
        });
    }

    private BalanceAssertions() {
        super();
    }

}
