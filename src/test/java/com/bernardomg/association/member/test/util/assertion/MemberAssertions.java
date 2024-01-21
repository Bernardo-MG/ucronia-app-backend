
package com.bernardomg.association.member.test.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.member.inbound.jpa.model.MemberEntity;

public final class MemberAssertions {

    public static void isEqualTo(final MemberEntity received, final MemberEntity expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getId())
                .withFailMessage("Expected id to not be null")
                .isNotNull();
            softly.assertThat(received.getName())
                .withFailMessage("Expected name '%s' but got '%s'", expected.getName(), received.getName())
                .isEqualTo(expected.getName());
            softly.assertThat(received.getSurname())
                .withFailMessage("Expected surname '%s' but got '%s'", expected.getSurname(), received.getSurname())
                .isEqualTo(expected.getSurname());
            softly.assertThat(received.getPhone())
                .withFailMessage("Expected phone '%s' but got '%s'", expected.getPhone(), received.getPhone())
                .isEqualTo(expected.getPhone());
            softly.assertThat(received.getIdentifier())
                .withFailMessage("Expected identifier '%s' but got '%s'", expected.getIdentifier(),
                    received.getIdentifier())
                .isEqualTo(expected.getIdentifier());
        });
    }

    private MemberAssertions() {
        super();
    }

}
