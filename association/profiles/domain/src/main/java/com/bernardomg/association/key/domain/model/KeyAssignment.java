
package com.bernardomg.association.key.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public record KeyAssignment(Long number, Boolean available, String description, Optional<AssignedProfile> profile) {

    public KeyAssignment(final Long number, final Boolean available, final String description,
            final Optional<AssignedProfile> profile) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(available, "Available flag can't be null");
        Objects.requireNonNull(description, "Description can't be null");
        Objects.requireNonNull(profile, "Profile can't be null");

        this.number = number;
        this.available = available;
        this.description = StringUtils.trim(description);
        this.profile = profile;
    }

    public record AssignedProfile(Long number, String name) {

        public AssignedProfile(final Long number, final String name) {
            Objects.requireNonNull(number, "Number can't be null");
            Objects.requireNonNull(name, "Name can't be null");

            this.number = number;
            this.name = StringUtils.trim(name);
        }

    }

}
