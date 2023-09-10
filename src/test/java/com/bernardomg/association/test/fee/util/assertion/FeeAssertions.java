
package com.bernardomg.association.test.fee.util.assertion;

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
        Assertions.assertThat(received.getMemberName())
            .withFailMessage("Expected name '%s' but got '%s'", expected.getMemberName(), received.getMemberName())
            .isEqualTo(expected.getMemberName());
        Assertions.assertThat(received.getDate())
            .withFailMessage("Expected date '%s' but got '%s'", expected.getDate(), received.getDate())
            .isEqualTo(expected.getDate());
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
        Assertions.assertThat(received.getDate())
            .withFailMessage("Expected date '%s' but got '%s'", expected.getDate(), received.getDate())
            .isEqualTo(expected.getDate());
        Assertions.assertThat(received.getPaid())
            .withFailMessage("Expected paid flag '%s' but got '%s'", expected.getPaid(), received.getPaid())
            .isEqualTo(expected.getPaid());
    }

}
