
package com.bernardomg.association.person.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record Person(String identifier, Long number, PersonName name, String phone) {

}
