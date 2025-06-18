
package com.bernardomg.association.person.domain.filter;

public record PersonFilter(PersonStatus status, String name) {

    public PersonFilter {
        // TODO: reject nulls
        if (status == null) {
            status = PersonStatus.ALL;
        }
        if (name == null) {
            name = "";
        }
    }

    public enum PersonStatus {
        ACTIVE, ALL, ALL_MEMBER, INACTIVE, NO_MEMBER
    }

}
