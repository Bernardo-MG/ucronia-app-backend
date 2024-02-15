
package com.bernardomg.association.library.domain.model;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Book {

    private Collection<Author> authors;

    private String             isbn;

    private GameSystem         system;

    private String             title;

    private BookType           type;

}
