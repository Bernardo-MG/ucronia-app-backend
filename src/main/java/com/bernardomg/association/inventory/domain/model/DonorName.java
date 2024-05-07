
package com.bernardomg.association.inventory.domain.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class DonorName {

    private String firstName;

    private String lastName;

    public DonorName(final String firstName, final String lastName) {
        super();

        this.firstName = StringUtils.trim(firstName);
        this.lastName = StringUtils.trim(lastName);
    }

    public final String getFullName() {
        return String.format("%s %s", firstName, lastName)
            .trim();
    }

}
