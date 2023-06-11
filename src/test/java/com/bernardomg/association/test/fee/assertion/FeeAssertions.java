
package com.bernardomg.association.test.fee.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.persistence.model.PersistentFee;

public final class FeeAssertions {

    public static final void isEqualTo(final MemberFee received, final MemberFee expected) {
        Assertions.assertThat(received.getId())
            .isNotNull();
        Assertions.assertThat(received.getMemberId())
            .isEqualTo(expected.getId());
        Assertions.assertThat(received.getName())
            .isEqualTo(expected.getName());
        Assertions.assertThat(received.getSurname())
            .isEqualTo(expected.getSurname());
        Assertions.assertThat(received.getDate()
            .getTime())
            .isEqualTo(expected.getDate()
                .getTime());
        Assertions.assertThat(received.getPaid())
            .isEqualTo(expected.getPaid());
    }

    public static final void isEqualTo(final PersistentFee received, final MemberFee expected) {
        Assertions.assertThat(received.getId())
            .isNotNull();
        Assertions.assertThat(received.getMemberId())
            .isEqualTo(expected.getId());
        Assertions.assertThat(received.getDate()
            .getTime())
            .isEqualTo(expected.getDate()
                .getTime());
        Assertions.assertThat(received.getPaid())
            .isEqualTo(expected.getPaid());
    }

}
