
package com.bernardomg.association.inventory.domain.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;

/**
 * TODO: test this
 */
@Builder(setterPrefix = "with")
public record DonorName(String firstName, String lastName) {

    public DonorName(final String firstName, final String lastName) {
        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);
    }

    public final String getFullName() {
        return String.format("%s %s", firstName, lastName)
            .trim();
    }

}
