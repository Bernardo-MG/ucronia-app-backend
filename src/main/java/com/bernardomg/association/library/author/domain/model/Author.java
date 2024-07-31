
package com.bernardomg.association.library.author.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class Author {

    private String name;

}
