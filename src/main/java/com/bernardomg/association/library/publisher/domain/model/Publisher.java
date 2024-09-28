
package com.bernardomg.association.library.publisher.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record Publisher(String name) {

}
