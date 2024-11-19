
package com.bernardomg.association.person.adapter.outbound.rest.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class PersonChange {

    public record Membership(Boolean active, Boolean renew) {

    }

    private LocalDate        birthDate;

    private String           identifier;

    private Membership       membership;

    private PersonChangeName name;

    private String           phone;

}
