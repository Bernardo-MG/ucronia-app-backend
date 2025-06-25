
package com.bernardomg.association.person.adapter.outbound.rest.model;

import java.time.LocalDate;

public final record PersonChange(String identifier, PersonChangeName name, Membership membership, LocalDate birthDate) {

    public record Membership(Boolean active, Boolean renew) {

    }

}
