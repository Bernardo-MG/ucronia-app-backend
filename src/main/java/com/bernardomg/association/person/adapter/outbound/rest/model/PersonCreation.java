
package com.bernardomg.association.person.adapter.outbound.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class PersonCreation {

    public record Membership(Boolean active) {

    }

    private Membership       membership;

    private PersonChangeName name;

}
