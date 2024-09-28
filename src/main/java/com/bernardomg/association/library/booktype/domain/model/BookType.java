
package com.bernardomg.association.library.booktype.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record BookType(String name) {

}
