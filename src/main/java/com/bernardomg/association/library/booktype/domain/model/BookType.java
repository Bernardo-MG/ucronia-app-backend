
package com.bernardomg.association.library.booktype.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class BookType {

    private String name;

}
