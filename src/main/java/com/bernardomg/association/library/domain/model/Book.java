
package com.bernardomg.association.library.domain.model;

import java.util.Collection;

import com.bernardomg.association.inventory.domain.model.Donor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Book {

    private Collection<Author> authors;

    private BookType           bookType;

    private Collection<Donor>  donors;

    private GameSystem         gameSystem;

    private String             isbn;

    private String             language;

    private Long               number;

    private Publisher          publisher;

    private String             title;

}
