
package com.bernardomg.association.person.domain.query;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PersonQuery(PersonStatus status, String name) {

    public PersonQuery {
        if (status == null) {
            status = PersonStatus.ALL;
        }
        if (name == null) {
            name = "";
        }
    }

}
