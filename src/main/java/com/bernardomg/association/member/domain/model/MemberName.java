
package com.bernardomg.association.member.domain.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Value;

/**
 * TODO: Try to make this immutable.
 */
@Value
@Builder(setterPrefix = "with")
public final class MemberName {

    private String firstName;

    private String lastName;

    public MemberName(final String firstName, final String lastName) {
        super();

        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);
    }

    public final String getFullName() {
        return String.format("%s %s", firstName, lastName)
            .trim();
    }

}
