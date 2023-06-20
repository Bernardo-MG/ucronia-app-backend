
package com.bernardomg.association.test.transaction.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

public final class TransactionAssertions {

    public static final void isEqualTo(final PersistentTransaction received, final PersistentTransaction expected) {
        Assertions.assertThat(received.getId())
            .isNotNull();
        Assertions.assertThat(received.getDescription())
            .isEqualTo(expected.getDescription());
        Assertions.assertThat(received.getDate()
            .getTime())
            .isEqualTo(expected.getDate()
                .getTime());
        Assertions.assertThat(received.getAmount())
            .isEqualTo(expected.getAmount());
    }

    public static final void isEqualTo(final Transaction received, final Transaction expected) {
        Assertions.assertThat(received.getId())
            .isNotNull();
        Assertions.assertThat(received.getDescription())
            .isEqualTo(expected.getDescription());
        Assertions.assertThat(received.getDate()
            .getTime())
            .isEqualTo(expected.getDate()
                .getTime());
        Assertions.assertThat(received.getAmount())
            .isEqualTo(expected.getAmount());
    }

}
