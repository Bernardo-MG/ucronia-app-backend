
package com.bernardomg.association.person.domain.model;

import java.util.Optional;

public record Person(String identifier, Long number, PersonName name, String phone, Optional<Membership> membership) {

    public record Membership(Boolean active) {

    }

}
