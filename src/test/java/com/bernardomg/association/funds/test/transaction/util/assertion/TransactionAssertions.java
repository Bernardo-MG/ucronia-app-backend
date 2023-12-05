
package com.bernardomg.association.funds.test.transaction.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;

public final class TransactionAssertions {

    public static final void isEqualTo(final PersistentTransaction received, final PersistentTransaction expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getId())
                .as("id")
                .withFailMessage("Expected id '%s' but got '%s'", expected.getId(), received.getId())
                .isNotNull();
            softly.assertThat(received.getDescription())
                .as("description")
                .withFailMessage("Expected description '%s' but got '%s'", expected.getDescription(),
                    received.getDescription())
                .isEqualTo(expected.getDescription());
            softly.assertThat(received.getDate())
                .as("date")
                .withFailMessage("Expected date '%s' but got '%s'", expected.getDate(), received.getDate())
                .isEqualTo(expected.getDate());
            softly.assertThat(received.getAmount())
                .as("amount")
                .withFailMessage("Expected amount '%s' but got '%s'", expected.getAmount(), received.getAmount())
                .isEqualTo(expected.getAmount());
        });
    }

    public static final void isEqualTo(final Transaction received, final Transaction expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getId())
                .as("id")
                .withFailMessage("Expected id '%s' but got '%s'", expected.getId(), received.getId())
                .isNotNull();
            softly.assertThat(received.getDescription())
                .as("description")
                .withFailMessage("Expected description '%s' but got '%s'", expected.getDescription(),
                    received.getDescription())
                .isEqualTo(expected.getDescription());
            softly.assertThat(received.getDate())
                .as("date")
                .withFailMessage("Expected date '%s' but got '%s'", expected.getDate(), received.getDate())
                .isEqualTo(expected.getDate());
            softly.assertThat(received.getAmount())
                .as("amount")
                .withFailMessage("Expected amount '%s' but got '%s'", expected.getAmount(), received.getAmount())
                .isEqualTo(expected.getAmount());
        });
    }

    private TransactionAssertions() {
        super();
    }

}
