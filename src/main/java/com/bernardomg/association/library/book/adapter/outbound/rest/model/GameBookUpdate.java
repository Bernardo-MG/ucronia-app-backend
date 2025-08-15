
package com.bernardomg.association.library.book.adapter.outbound.rest.model;

import java.time.Instant;
import java.util.Collection;

public record GameBookUpdate(String isbn, BookChangeTitle title, String language, Collection<Author> authors,
        Donation donation, Instant publishDate, Collection<Publisher> publishers, BookType bookType,
        GameSystem gameSystem) {

    public static record Author(Long number) {}

    public static record BookType(Long number) {}

    public static record Donation(Instant date, Collection<Donor> donors) {}

    public static record Donor(Long number) {}

    public static record GameSystem(Long number) {

    }

    public static record Publisher(Long number) {}

}
