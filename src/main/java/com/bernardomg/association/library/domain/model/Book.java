
package com.bernardomg.association.library.domain.model;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Book {

    private Collection<Author> authors;

    private BookType           bookType;

    private GameSystem         gameSystem;

    private Long               index;

    private String             isbn;

    private String             language;

    private String             title;

}
