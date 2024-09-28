
package com.bernardomg.association.library.author.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record Author(String name) {

}
