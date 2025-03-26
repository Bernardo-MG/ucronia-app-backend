
package com.bernardomg.association.person.domain.filter;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PersonFilter(PersonStatus status, String name) {

    public PersonFilter {
        if (status == null) {
            status = PersonStatus.ALL_MEMBER;
        }
        if (name == null) {
            name = "";
        }
    }

    public enum PersonStatus {
        ACTIVE, ALL, ALL_MEMBER, INACTIVE, NO_MEMBER
    }

}
