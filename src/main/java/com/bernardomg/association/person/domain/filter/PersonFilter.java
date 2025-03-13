
package com.bernardomg.association.person.domain.filter;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PersonFilter(PersonStatus status, String name) {

    public PersonFilter {
        if (status == null) {
            status = PersonStatus.ALL;
        }
        if (name == null) {
            name = "";
        }
    }

    public enum PersonStatus {
        ACTIVE, ALL, INACTIVE, NO_MEMBER
    }

}
