
package com.bernardomg.association.fee.domain.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FeeMember(Long number, MemberName name) {

    public record MemberName(String firstName, String lastName) {

        public MemberName(final String firstName, final String lastName) {
            this.firstName = StringUtils.trim(firstName);
            this.lastName = StringUtils.trim(lastName);
        }

        @JsonProperty("fullName")
        public final String fullName() {
            return String.format("%s %s", firstName, lastName)
                .trim();
        }

    }

}
