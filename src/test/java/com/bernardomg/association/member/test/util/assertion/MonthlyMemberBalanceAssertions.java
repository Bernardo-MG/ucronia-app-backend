
package com.bernardomg.association.member.test.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;

public final class MonthlyMemberBalanceAssertions {

    public static final void isEqualTo(final MonthlyMemberBalance received, final MonthlyMemberBalance expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getDate())
                .as("month")
                .withFailMessage("Expected month '%s' but got '%s'", expected.getDate(), received.getDate())
                .isEqualTo(expected.getDate());
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
