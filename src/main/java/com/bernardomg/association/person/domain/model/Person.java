
package com.bernardomg.association.person.domain.model;

import java.time.LocalDate;
import java.util.Optional;

public record Person(String identifier, Long number, PersonName name, LocalDate birthDate, String phone,
        Optional<Membership> membership) {

    public record Membership(Boolean active, Boolean renew) {

    }

}
