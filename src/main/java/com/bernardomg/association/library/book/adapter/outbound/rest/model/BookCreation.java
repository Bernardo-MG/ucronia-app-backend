
package com.bernardomg.association.library.book.adapter.outbound.rest.model;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class BookCreation {

    public record Author(Long number) {

    }

    public record BookType(Long number) {

    }

    public record Donor(Long number) {

    }

    public record GameSystem(Long number) {

    }

    public record Publisher(Long number) {

    }

    @Builder.Default
    private Collection<Author>    authors = List.of();

    /**
     * TODO: optional
     */
    private BookType              bookType;

    private Collection<Donor>     donors;

    /**
     * TODO: optional
     */
    private GameSystem            gameSystem;

    private String                isbn;

    private String                language;

    private Collection<Publisher> publishers;

    private String                title;

}
