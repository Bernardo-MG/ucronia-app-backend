
package com.bernardomg.association.person.adapter.outbound.rest.model;

public final record PersonCreation(PersonChangeName name, Membership membership) {

    public record Membership(Boolean active) {

    }

}
