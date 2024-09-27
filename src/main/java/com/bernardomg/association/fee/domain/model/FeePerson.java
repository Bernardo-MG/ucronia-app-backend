
package com.bernardomg.association.fee.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record FeePerson(String fullName, Long number) {

}
