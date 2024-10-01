
package com.bernardomg.association.person.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PublicPerson(Long number, PersonName name) {

}
