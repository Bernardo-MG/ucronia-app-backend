
package com.bernardomg.association.person.domain.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public record Person(String identifier, Long number, PersonName name, LocalDate birthDate,
        Optional<Membership> membership, Collection<PersonContact> contacts) {

    public record Membership(Boolean active, Boolean renew) {

    }

    public record PersonContact(ContactMode mode, String contact) {

    }

}
