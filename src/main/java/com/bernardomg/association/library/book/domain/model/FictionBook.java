
package com.bernardomg.association.library.book.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.publisher.domain.model.Publisher;

public record FictionBook(long number, Title title, String isbn, String language, Instant publishDate, boolean lent,
        Collection<Author> authors, Collection<BookLendingInfo> lendings, Collection<Publisher> publishers,
        Optional<Donation> donation) {

}
