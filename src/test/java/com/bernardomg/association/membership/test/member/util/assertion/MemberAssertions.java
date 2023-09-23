
package com.bernardomg.association.membership.test.member.util.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.persistence.model.PersistentMember;

public final class MemberAssertions {

    public static void isEqualTo(final Member received, final Member expected) {
        Assertions.assertThat(received.getId())
            .withFailMessage("Expected id to not be null")
            .isNotNull();
        Assertions.assertThat(received.getName())
            .withFailMessage("Expected name '%s' but got '%s'", expected.getName(), received.getName())
            .isEqualTo(expected.getName());
        Assertions.assertThat(received.getSurname())
            .withFailMessage("Expected surname '%s' but got '%s'", expected.getSurname(), received.getSurname())
            .isEqualTo(expected.getSurname());
        Assertions.assertThat(received.getPhone())
            .withFailMessage("Expected phone '%s' but got '%s'", expected.getPhone(), received.getPhone())
            .isEqualTo(expected.getPhone());
        Assertions.assertThat(received.getIdentifier())
            .withFailMessage("Expected identifier '%s' but got '%s'", expected.getIdentifier(),
                received.getIdentifier())
            .isEqualTo(expected.getIdentifier());
        Assertions.assertThat(received.getActive())
            .withFailMessage("Expected active flag '%s' but got '%s'", expected.getActive(), received.getActive())
            .isEqualTo(expected.getActive());
    }

    public static void isEqualTo(final PersistentMember received, final PersistentMember expected) {
        Assertions.assertThat(received.getId())
            .withFailMessage("Expected id to not be null")
            .isNotNull();
        Assertions.assertThat(received.getName())
            .withFailMessage("Expected name '%s' but got '%s'", expected.getName(), received.getName())
            .isEqualTo(expected.getName());
        Assertions.assertThat(received.getSurname())
            .withFailMessage("Expected surname '%s' but got '%s'", expected.getSurname(), received.getSurname())
            .isEqualTo(expected.getSurname());
        Assertions.assertThat(received.getPhone())
            .withFailMessage("Expected phone '%s' but got '%s'", expected.getPhone(), received.getPhone())
            .isEqualTo(expected.getPhone());
        Assertions.assertThat(received.getIdentifier())
            .withFailMessage("Expected identifier '%s' but got '%s'", expected.getIdentifier(),
                received.getIdentifier())
            .isEqualTo(expected.getIdentifier());
        Assertions.assertThat(received.getActive())
            .withFailMessage("Expected active flag '%s' but got '%s'", expected.getActive(), received.getActive())
            .isEqualTo(expected.getActive());
    }

}