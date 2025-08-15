
package com.bernardomg.association.person.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

public record Person(String identifier, Long number, PersonName name, Instant birthDate,
        Optional<Membership> membership, Collection<PersonContact> contacts) {

    public record Membership(Boolean active, Boolean renew) {

    }

    public record PersonContact(ContactMethod method, String contact) {

    }

}
