
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

    public record Author(Long number, String name) {

    }

    public record BookType(String name) {

    }

    public record Donor(Long number) {

    }

    public record GameSystem(Long number, String name) {

    }

    public record Publisher(Long number, String name) {

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
