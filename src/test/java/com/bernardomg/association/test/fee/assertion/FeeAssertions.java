
package com.bernardomg.association.test.fee.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.persistence.model.PersistentFee;

public final class FeeAssertions {

    public static final void isEqualTo(final MemberFee received, final MemberFee expected) {
        Assertions.assertThat(received.getId())
            .withFailMessage("Expected id to not be null")
            .isNotNull();
        Assertions.assertThat(received.getMemberId())
            .withFailMessage("Expected member id '%s' but got '%s'", expected.getMemberId(), received.getMemberId())
            .isEqualTo(expected.getMemberId());
        Assertions.assertThat(received.getName())
            .withFailMessage("Expected name '%s' but got '%s'", expected.getName(), received.getName())
            .isEqualTo(expected.getName());
        Assertions.assertThat(received.getSurname())
            .withFailMessage("Expected surname '%s' but got '%s'", expected.getSurname(), received.getSurname())
            .isEqualTo(expected.getSurname());
        Assertions.assertThat(received.getDate()
            .getTime())
            .withFailMessage("Expected date '%s' but got '%s'", received.getDate(), expected.getDate())
            .isEqualTo(expected.getDate()
                .getTime());
        Assertions.assertThat(received.getPaid())
            .withFailMessage("Expected paid flag '%s' but got '%s'", expected.getPaid(), received.getPaid())
            .isEqualTo(expected.getPaid());
    }

    public static final void isEqualTo(final PersistentFee received, final PersistentFee expected) {
        Assertions.assertThat(received.getId())
            .withFailMessage("Expected id to not be null")
            .isNotNull();
        Assertions.assertThat(received.getMemberId())
            .withFailMessage("Expected member id '%s' but got '%s'", expected.getMemberId(), received.getMemberId())
            .isEqualTo(expected.getMemberId());
        Assertions.assertThat(received.getDate()
            .getTime())
            .withFailMessage("Expected date '%s' but got '%s'", received.getDate(), expected.getDate())
            .isEqualTo(expected.getDate()
                .getTime());
        Assertions.assertThat(received.getPaid())
            .withFailMessage("Expected paid flag '%s' but got '%s'", expected.getPaid(), received.getPaid())
            .isEqualTo(expected.getPaid());
    }

}
