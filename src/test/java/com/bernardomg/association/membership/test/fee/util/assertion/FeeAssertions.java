
package com.bernardomg.association.membership.test.fee.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;

public final class FeeAssertions {

    public static final void isEqualTo(final FeeEntity received, final FeeEntity expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getId())
                .withFailMessage("Expected id to not be null")
                .isNotNull();
            softly.assertThat(received.getMemberId())
                .withFailMessage("Expected member id '%s' but got '%s'", expected.getMemberId(), received.getMemberId())
                .isEqualTo(expected.getMemberId());
            softly.assertThat(received.getDate())
                .withFailMessage("Expected date '%s' but got '%s'", expected.getDate(), received.getDate())
                .isEqualTo(expected.getDate());
        });
    }

}
