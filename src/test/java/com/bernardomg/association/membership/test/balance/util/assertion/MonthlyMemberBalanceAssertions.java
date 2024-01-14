
package com.bernardomg.association.membership.test.balance.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.member.model.MonthlyMemberBalance;

public final class MonthlyMemberBalanceAssertions {

    public static final void isEqualTo(final MonthlyMemberBalance received, final MonthlyMemberBalance expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getMonth())
                .as("month")
                .withFailMessage("Expected month '%s' but got '%s'", expected.getMonth(), received.getMonth())
                .isEqualTo(expected.getMonth());
            softly.assertThat(received.getTotal())
                .as("total")
                .withFailMessage("Expected total '%s' but got '%s'", expected.getTotal(), received.getTotal())
                .isEqualTo(expected.getTotal());
        });
    }

    private MonthlyMemberBalanceAssertions() {
        super();
    }

}
