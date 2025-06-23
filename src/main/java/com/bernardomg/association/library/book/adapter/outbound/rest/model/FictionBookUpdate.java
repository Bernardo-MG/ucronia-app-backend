
package com.bernardomg.association.library.book.adapter.outbound.rest.model;

import java.time.LocalDate;
import java.util.Collection;

public record FictionBookUpdate(String isbn, BookChangeTitle title, String language, Collection<Author> authors,
        Donation donation, LocalDate publishDate, Collection<Publisher> publishers) {

    public static record Author(Long number) {}

    public static record Donation(LocalDate date, Collection<Donor> donors) {}

    public static record Donor(Long number) {}

    public static record Publisher(Long number) {}

}
