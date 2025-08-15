
package com.bernardomg.association.person.adapter.outbound.rest.model;

import java.time.Instant;

public final record PersonChange(String identifier, PersonChangeName name, Membership membership, Instant birthDate) {

    public record Membership(Boolean active, Boolean renew) {

    }

}
