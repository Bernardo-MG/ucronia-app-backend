
package com.bernardomg.association.person.adapter.outbound.rest.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class PersonChange {

    public record Membership(boolean active) {

    }

    private String           identifier;

    private Membership       membership;

    @NotNull
    @Valid
    private PersonChangeName name;

    private String           phone;

}
