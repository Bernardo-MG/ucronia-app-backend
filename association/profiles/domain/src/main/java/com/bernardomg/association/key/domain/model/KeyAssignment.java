
package com.bernardomg.association.key.domain.model;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public record KeyAssignment(Long number, Boolean available, String description, Optional<AssignedMember> member) {

    public KeyAssignment(final Long number, final Boolean available, final String description,
            final Optional<AssignedMember> member) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(available, "Available flag can't be null");
        Objects.requireNonNull(description, "Description can't be null");
        Objects.requireNonNull(member, "Member can't be null");

        this.number = number;
        this.available = available;
        this.description = StringUtils.trim(description);
        this.member = member;
    }

    public record AssignedMember(Long number, Long memberNumber, String name) {

        public AssignedMember(final Long number, final Long memberNumber, final String name) {
            Objects.requireNonNull(number, "Number can't be null");
            Objects.requireNonNull(memberNumber, "Member number flag can't be null");
            Objects.requireNonNull(name, "Name can't be null");

            this.number = number;
            this.memberNumber = memberNumber;
            this.name = StringUtils.trim(name);
        }

    }

}
