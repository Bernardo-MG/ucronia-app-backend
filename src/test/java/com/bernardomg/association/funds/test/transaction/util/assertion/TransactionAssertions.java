
package com.bernardomg.association.funds.test.transaction.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;

public final class TransactionAssertions {

    public static final void isEqualTo(final PersistentTransaction received, final PersistentTransaction expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getId())
                .as("id")
                .isNotNull();
            softly.assertThat(received.getDescription())
                .as("description")
                .isEqualTo(expected.getDescription());
            softly.assertThat(received.getDate())
                .as("date")
                .isEqualTo(expected.getDate());
            softly.assertThat(received.getAmount())
                .as("amount")
                .isEqualTo(expected.getAmount());
        });
    }

    public static final void isEqualTo(final Transaction received, final Transaction expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getId())
                .as("id")
                .isNotNull();
            softly.assertThat(received.getDescription())
                .as("description")
                .isEqualTo(expected.getDescription());
            softly.assertThat(received.getDate())
                .as("date")
                .isEqualTo(expected.getDate());
            softly.assertThat(received.getAmount())
                .as("amount")
                .isEqualTo(expected.getAmount());
        });
    }

}
