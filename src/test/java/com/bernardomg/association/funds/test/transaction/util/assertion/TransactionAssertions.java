
package com.bernardomg.association.funds.test.transaction.util.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;

public final class TransactionAssertions {

    public static final void isEqualTo(final PersistentTransaction received, final PersistentTransaction expected) {
        Assertions.assertThat(received.getId())
            .isNotNull();
        Assertions.assertThat(received.getDescription())
            .isEqualTo(expected.getDescription());
        Assertions.assertThat(received.getDate())
            .isEqualTo(expected.getDate());
        Assertions.assertThat(received.getAmount())
            .isEqualTo(expected.getAmount());
    }

    public static final void isEqualTo(final Transaction received, final Transaction expected) {
        Assertions.assertThat(received.getId())
            .isNotNull();
        Assertions.assertThat(received.getDescription())
            .isEqualTo(expected.getDescription());
        Assertions.assertThat(received.getDate())
            .isEqualTo(expected.getDate());
        Assertions.assertThat(received.getAmount())
            .isEqualTo(expected.getAmount());
    }

}
