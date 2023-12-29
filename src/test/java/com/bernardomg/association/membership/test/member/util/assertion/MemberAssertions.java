
package com.bernardomg.association.membership.test.member.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;

public final class MemberAssertions {

    public static void isEqualTo(final Member received, final Member expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getNumber())
                .withFailMessage("Expected number '%d' but got '%d'", expected.getNumber(), received.getNumber())
                .isEqualTo(expected.getNumber());
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
            softly.assertThat(received.isActive())
                .withFailMessage("Expected active flag '%s' but got '%s'", expected.isActive(), received.isActive())
                .isEqualTo(expected.isActive());
        });
    }

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

}
